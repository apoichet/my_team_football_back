package com.myteam.usecase

import com.myteam.domain.*
import com.myteam.exception.UserAccountUnknown
import com.myteam.exception.UserMailAlreadyExist
import com.myteam.port.TeamMemberRepository
import com.myteam.port.UserRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime

internal class UserAccountTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var mockTeamMember: TeamMemberRepository
    private lateinit var sut: UserAccount

    @BeforeEach
    fun setUp() {
        mockRepository = mock()
        mockTeamMember = mock()
        sut = UserAccount(mockRepository, mockTeamMember)
    }

    @Test
    fun `should create new valid user account`() {
        //Given
        val user = buildUser("0", "mail", "password")
        val userSaved = buildUser("1", "mail", "password")
        //When
        whenever(mockRepository.findBy("mail")).thenReturn(null)
        whenever(mockRepository.create(user)).thenReturn(userSaved)
        val userExpected = sut.create(user)
        //Then
        verify(mockRepository).findBy("mail")
        verify(mockRepository).create(user)
        assertEquals(userSaved, userExpected)
    }

    @Test
    fun `should reject creation when user mail already exists`() {
        //When
        val userAlreadyExists = buildUser("1", "MAIL_ALREADY_EXISTS", "password")
        whenever(mockRepository.findBy("MAIL_ALREADY_EXISTS")).thenReturn(userAlreadyExists)
        //Then
        assertThrows<UserMailAlreadyExist> {
            sut.create(userAlreadyExists)
        }
    }

    @Test
    fun `should log existing user`() {
        //Given
        val user = buildUser("1", "mail", "password")
        user.teams = listOf(buildTeam())
        //When
        whenever(mockRepository.findBy("mail")).thenReturn(user)
        val userExpected = sut.login("mail", "password")
        //Then
        verify(mockRepository).findBy("mail")
        assertEquals(user, userExpected)
    }

    @Test
    fun `should not log unknown login`() {
        //When
        whenever(mockRepository.findBy("mail")).thenReturn(null)
        val userExpected = sut.login("mail", "password")
        //Then
        verify(mockRepository).findBy("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should not log unknown password`() {
        //Given
        val user = buildUser("1", "mail", "password")
        //When
        whenever(mockRepository.findBy("mail")).thenReturn(user)
        val userExpected = sut.login("mail", "other_password")
        //Then
        verify(mockRepository).findBy("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should close existing account`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockRepository.find("1")).thenReturn(existingUser)
        whenever(mockRepository.delete(existingUser)).thenReturn(true)
        val responseClose = sut.close(existingUser)
        //Then
        verify(mockRepository).find("1")
        verify(mockRepository).delete(existingUser)
        assertTrue(responseClose)
    }

    @Test
    fun `should close account unknown`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockRepository.find("1")).thenReturn(null)
        val responseClose = sut.close(existingUser)
        //Then
        verify(mockRepository).find("1")
        verify(mockRepository, never()).delete(existingUser)
        assertTrue(responseClose)
    }

    @Test
    fun `should let modify user`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        val userModified = buildUser("1", "mail", "new_password")
        //When
        whenever(mockRepository.find("1")).thenReturn(existingUser)
        whenever(mockRepository.update(existingUser)).thenReturn(userModified)
        val userReturn = sut.modify(existingUser)
        //Then
        verify(mockRepository).find("1")
        verify(mockRepository).update(existingUser)
        assertEquals(userModified, userReturn)
    }

    @Test
    fun `should modify team members information when modify user`() {
        //Given
        val userModify = buildUser("1", "new_mail", "password")
        val userFound = buildUser("1", "mail", "password")
        val team = buildTeam()
        team.players = listOf(buildPlayer("mail"))
        team.coach = buildCoach("mail")
        userModify.teams = listOf(team)
        userFound.teams = listOf(team)
        //When
        whenever(mockRepository.find("1")).thenReturn(userFound)
        whenever(mockTeamMember.update(team.players.first())).thenReturn(team.players.first())
        whenever(mockTeamMember.update(team.coach)).thenReturn(team.coach)
        whenever(mockRepository.update(userModify)).thenReturn(userModify)
        val userReturn = sut.modify(userModify)
        //Then
        verify(mockRepository).update(userModify)
        verify(mockTeamMember).update(team.players.first())
        verify(mockTeamMember).update(team.coach)
        assertEquals(userReturn?.teams?.first()?.coach?.mail, "new_mail")
        assertEquals(userReturn?.teams?.first()?.players?.first()?.mail, "new_mail")
    }

    @Test
    fun `should reject when modify user unknown`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockRepository.find("1")).thenReturn(null)
        //Then
        assertThrows<UserAccountUnknown> { sut.modify(existingUser) }
        verify(mockRepository).find("1")
        verify(mockRepository, never()).update(existingUser)
    }

    private fun buildUser(id: String, mail: String, password: String): User {
        return User(id = id,
            mail = mail,
            password = password,
            firstName = "firstname",
            lastName = "lastname",
            birthdate = LocalDate.now(),
            phone = "",
            adress = emptyList()
        )
    }

    private fun buildTeam(): Team {
        return Team(name = "name",
            creationDate = LocalDateTime.now(),
            licenceAmount = 0.0f,
            homeStadium = Stadium("name", Adress("", "", "")),
            coach = Coach("mail", "firstName", "lastName", LocalDate.now(), "phone", emptyList())
        )
    }


    private fun buildPlayer(mail : String): Player {
        return Player(mail = mail,
            firstName = "firstName",
            lastName = "lastName",
            birthdate = LocalDate.now(),
            phone = "phone",
            adress = emptyList(),
            strongFoot = PlayerFoot.BOTH,
            positions = emptyList(),
            originalPosition = PlayerPosition.GK
        )

    }

    private fun buildCoach(mail: String): Coach {
        return Coach(mail = mail,
            firstName = "firstName",
            lastName = "lastName",
            birthdate = LocalDate.now(),
            phone = "phone",
            adress = emptyList()
        )

    }

}