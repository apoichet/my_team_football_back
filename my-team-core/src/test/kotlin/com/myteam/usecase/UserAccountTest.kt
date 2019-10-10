package com.myteam.usecase

import com.myteam.domain.*
import com.myteam.exception.TeamAlreadyExists
import com.myteam.exception.TeamNotExists
import com.myteam.exception.UserAccountUnknown
import com.myteam.exception.UserMailAlreadyExist
import com.myteam.repository.TeamMemberRepository
import com.myteam.repository.TeamRepository
import com.myteam.repository.UserRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

internal class UserAccountTest {

    private lateinit var mockUserRepo: UserRepository
    private lateinit var mockTeamRepo: TeamRepository
    private lateinit var mockTeamMemberRepo: TeamMemberRepository
    private lateinit var sut: UserAccount

    @BeforeEach
    fun setUp() {
        mockUserRepo = mock()
        mockTeamMemberRepo = mock()
        mockTeamRepo = mock()
        sut = UserAccount(mockUserRepo, mockTeamRepo, mockTeamMemberRepo)
    }

    @Test
    fun `should create new valid user account`() {
        //Given
        val user = buildUser("0", "mail", "password")
        val userSaved = buildUser("1", "mail", "password")
        //When
        whenever(mockUserRepo.findBy("mail")).thenReturn(null)
        whenever(mockUserRepo.create(user)).thenReturn(userSaved)
        val userExpected = sut.createAccount(user)
        //Then
        verify(mockUserRepo).findBy("mail")
        verify(mockUserRepo).create(user)
        assertEquals(userSaved, userExpected)
    }

    @Test
    fun `should reject creation when user mail already exists`() {
        //When
        val userAlreadyExists = buildUser("1", "MAIL_ALREADY_EXISTS", "password")
        whenever(mockUserRepo.findBy("MAIL_ALREADY_EXISTS")).thenReturn(userAlreadyExists)
        //Then
        assertThrows<UserMailAlreadyExist> {
            sut.createAccount(userAlreadyExists)
        }
    }

    @Test
    fun `should log existing user`() {
        //Given
        val user = buildUser("1", "mail", "password")
        user.teams = listOf(buildTeam("1"))
        //When
        whenever(mockUserRepo.findBy("mail")).thenReturn(user)
        val userExpected = sut.loginUser("mail", "password")
        //Then
        verify(mockUserRepo).findBy("mail")
        assertEquals(user, userExpected)
    }

    @Test
    fun `should not log unknown login`() {
        //When
        whenever(mockUserRepo.findBy("mail")).thenReturn(null)
        val userExpected = sut.loginUser("mail", "password")
        //Then
        verify(mockUserRepo).findBy("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should not log unknown password`() {
        //Given
        val user = buildUser("1", "mail", "password")
        //When
        whenever(mockUserRepo.findBy("mail")).thenReturn(user)
        val userExpected = sut.loginUser("mail", "other_password")
        //Then
        verify(mockUserRepo).findBy("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should close existing account`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockUserRepo.find("1")).thenReturn(existingUser)
        whenever(mockUserRepo.delete(existingUser)).thenReturn(true)
        val responseClose = sut.closeAccount(existingUser)
        //Then
        verify(mockUserRepo).find("1")
        verify(mockUserRepo).delete(existingUser)
        assertTrue(responseClose)
    }

    @Test
    fun `should close account unknown`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockUserRepo.find("1")).thenReturn(null)
        val responseClose = sut.closeAccount(existingUser)
        //Then
        verify(mockUserRepo).find("1")
        verify(mockUserRepo, never()).delete(existingUser)
        assertTrue(responseClose)
    }

    @Test
    fun `should let modify user`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        val userModified = buildUser("1", "mail", "new_password")
        //When
        whenever(mockUserRepo.find("1")).thenReturn(existingUser)
        whenever(mockUserRepo.update(existingUser)).thenReturn(userModified)
        val userReturn = sut.modifyProfile(existingUser)
        //Then
        verify(mockUserRepo).find("1")
        verify(mockUserRepo).update(existingUser)
        assertEquals(userModified, userReturn)
    }

    @Test
    fun `should modify team members information when modify user`() {
        //Given
        val userModify = buildUser("1", "new_mail", "password")
        val userFound = buildUser("1", "mail", "password")
        val team = buildTeam("1")
        team.players = listOf(buildPlayer("mail"))
        team.president = buildTeamMember("mail")
        team.coach = buildTeamMember("mail")
        userModify.teams = listOf(team)
        userFound.teams = listOf(team)
        //When
        whenever(mockUserRepo.find("1")).thenReturn(userFound)
        whenever(mockTeamMemberRepo.update(team.players.first())).thenReturn(team.players.first())
        whenever(mockTeamMemberRepo.update(team.president)).thenReturn(team.coach)
        whenever(mockUserRepo.update(userModify)).thenReturn(userModify)
        val userReturn = sut.modifyProfile(userModify)
        //Then
        verify(mockUserRepo).update(userModify)
        verify(mockTeamMemberRepo).update(team.players.first())
        verify(mockTeamMemberRepo).update(team.president)
        assertEquals(userReturn?.teams?.first()?.president?.contact?.mail, "new_mail")
        assertEquals(userReturn?.teams?.first()?.coach?.contact?.mail, "new_mail")
        assertEquals(userReturn?.teams?.first()?.players?.first()?.contact?.mail, "new_mail")
    }

    @Test
    fun `should reject when modify user unknown`() {
        //Given
        val existingUser = buildUser("1", "mail", "password")
        //When
        whenever(mockUserRepo.find("1")).thenReturn(null)
        //Then
        assertThrows<UserAccountUnknown> { sut.modifyProfile(existingUser) }
        verify(mockUserRepo).find("1")
        verify(mockUserRepo, never()).update(existingUser)
    }

    @Test
    fun `should find existing team`() {
        //Given
        val team = buildTeam("1")
        //When
        whenever(mockTeamRepo.find("1")).thenReturn(team)
        val teamExpected = sut.findTeam("1")
        //Then
        verify(mockTeamRepo).find("1")
        assertEquals(teamExpected, team)
    }

    @Test
    fun `should not find unknown team`() {
        //When
        whenever(mockTeamRepo.find("1")).thenReturn(null)
        val teamExpected = sut.findTeam("1")
        //Then
        verify(mockTeamRepo).find("1")
        assertNull(teamExpected)
    }

    @Test
    fun `should create new team with token`() {
        //Given
        val user = buildUser("1", "mail", "password")
        val teamWithoutToken = buildTeam("")
        val newTeam = buildTeam("token")
        //When
        whenever(mockTeamRepo.create(teamWithoutToken)).thenReturn(newTeam)
        val teamReturn = sut.createTeam(user, teamWithoutToken)
        //Then
        verify(mockTeamRepo). create(teamWithoutToken)
        assertEquals(teamReturn, newTeam)
    }

    @Test
    fun `should reject creation team when user has already created a team with the same name`() {
        //Given
        val user = buildUser("1", "mail", "password")
        val userTeam = buildTeam("token")
        userTeam.name = "name"
        user.teams = listOf(userTeam)
        val newTeam = buildTeam("")
        //When
        assertThrows<TeamAlreadyExists> {
            sut.createTeam(user, newTeam)
        }
    }

    @Test
    fun `should join existing team`() {
        //Given
        val team = buildTeam("token")
        val player = buildPlayer("mail")
        val teamWithNewPlayer = buildTeam("token")
        teamWithNewPlayer.players = listOf(player)
        //When
        whenever(mockTeamRepo.find(team.token)).thenReturn(team)
        whenever(mockTeamRepo.update(team)).thenReturn(teamWithNewPlayer)
        val teamReturn = sut.joinTeam(player, team)
        //Then
        verify(mockTeamRepo).find(team.token)
        verify(mockTeamRepo).update(team)
        assertTrue(teamReturn.players.contains(player))

    }

    @Test
    fun `should reject when join unknown team`() {
        //Given
        val team = buildTeam("token")
        val player = buildPlayer("mail")
        //When
        whenever(mockTeamRepo.find(team.token)).thenReturn(null)
        assertThrows<TeamNotExists> {
            verify(mockTeamRepo, never()).update(team)
            sut.joinTeam(player, team)
        }

    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firtsname", "lastname")
    }

    private fun buildUser(id: String, mail: String, password: String): User {
        return User(id = id,
            contact = buildContact(mail),
            password = password
        )
    }

    private fun buildTeam(token: String): Team {
        return Team(token = token,
            name = "name",
            creationDate = LocalDateTime.now(),
            licenceAmount = 0.0f,
            homeStadium = Stadium("name", Adress("", "", "")),
            president = buildTeamMember("mail")
        )
    }


    private fun buildPlayer(mail : String): Player {
        return Player(contact = buildContact(mail),
            strongFoot = PlayerFoot.BOTH,
            positions = emptyList(),
            originalPosition = PlayerPosition.GK
        )

    }

    private fun buildTeamMember(mail: String): TeamMember {
        return TeamMember(contact = buildContact(mail))

    }

}