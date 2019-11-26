package com.myteam.core.usecase

import com.myteam.core.domain.*
import com.myteam.core.enums.PlayerFoot
import com.myteam.core.enums.PlayerPosition
import com.myteam.core.exception.TeamAlreadyExists
import com.myteam.core.exception.UserMailAlreadyExist
import com.myteam.infra.TeamRepository
import com.myteam.infra.UserRepository
import com.nhaarman.mockitokotlin2.mock
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
    private lateinit var sut: UserAccount

    @BeforeEach
    fun setUp() {
        mockUserRepo = mock()
        mockTeamRepo = mock()
        sut = UserAccount(mockUserRepo, mockTeamRepo)
    }

    @Test
    fun `should create new valid user account`() {
        //Given
        val user = buildUser( "mail", "password")
        val userSaved = buildUser("mail", "password")
        //When
        whenever(mockUserRepo.findByMail("mail")).thenReturn(null)
        whenever(mockUserRepo.create(user)).thenReturn(userSaved)
        val userExpected = sut.createAccount(user)
        //Then
        verify(mockUserRepo).findByMail("mail")
        verify(mockUserRepo).create(user)
        assertEquals(userSaved, userExpected)
    }

    @Test
    fun `should reject creation when user mail already exists`() {
        //When
        val userAlreadyExists = buildUser("MAIL_ALREADY_EXISTS", "password")
        whenever(mockUserRepo.findByMail("MAIL_ALREADY_EXISTS")).thenReturn(userAlreadyExists)
        //Then
        assertThrows<UserMailAlreadyExist> {
            sut.createAccount(userAlreadyExists)
        }
    }

    @Test
    fun `should log existing user`() {
        //Given
        val user = buildUser( "mail", "password")
        user.teams = listOf(buildTeam("1", "name", "season"))
        //When
        whenever(mockUserRepo.findByMail("mail")).thenReturn(user)
        val userExpected = sut.loginUser("mail", "password")
        //Then
        verify(mockUserRepo).findByMail("mail")
        assertEquals(user, userExpected)
    }

    @Test
    fun `should not log user with unknown mail`() {
        //When
        whenever(mockUserRepo.findByMail("mail")).thenReturn(null)
        val userExpected = sut.loginUser("mail", "password")
        //Then
        verify(mockUserRepo).findByMail("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should not log user with unknown password`() {
        //Given
        val user = buildUser( "mail", "password")
        //When
        whenever(mockUserRepo.findByMail("mail")).thenReturn(user)
        val userExpected = sut.loginUser("mail", "other_password")
        //Then
        verify(mockUserRepo).findByMail("mail")
        assertNull(userExpected)
    }

    @Test
    fun `should close existing account`() {
        //Given
        val existingUser = buildUser("mail", "password")
        //When
        whenever(mockUserRepo.delete(existingUser)).thenReturn(true)
        val responseClose = sut.closeAccount(existingUser)
        //Then
        verify(mockUserRepo).delete(existingUser)
        assertTrue(responseClose)
    }

    @Test
    fun `should let modify user contact`() {
        //Given
        val existingUser = buildUser("mail", "password")
        val contactModified = buildContact("new_mail")
        val userModified = buildUser("new_mail", "password")
        userModified.userTeam.contact = contactModified
        //When
        whenever(mockUserRepo.updateContact(existingUser, contactModified)).thenReturn(userModified)
        val userReturn = sut.modifyContact(existingUser, contactModified)
        //Then
        verify(mockUserRepo).updateContact(existingUser, contactModified)
        assertEquals(contactModified, userReturn?.userTeam?.contact)
    }

    @Test
    fun `should reject modify user contact with mail already exists`() {
        //Given
        val existingUser = buildUser("mail", "password")
        val contactModified = buildContact("mail")
        //Then
        whenever(mockUserRepo.findByMail(contactModified.mail)).thenReturn(existingUser)
        assertThrows<UserMailAlreadyExist> {
            sut.modifyContact(existingUser, contactModified)
        }
    }

    @Test
    fun `should let modify user password`() {
        //Given
        val existingUser = buildUser( "mail", "password")
        val newPassword = "new_password"
        val userModified = buildUser( "mail", newPassword)
        //When
        whenever(mockUserRepo.updatePassword(existingUser, newPassword)).thenReturn(userModified)
        val userReturn = sut.modifyPassword(existingUser, newPassword)
        //Then
        verify(mockUserRepo).updatePassword(existingUser, newPassword)
        assertEquals(newPassword, userReturn?.password)
    }

    @Test
    fun `should create new team with token`() {
        //Given
        val user = buildUser("mail", "password")
        val teamWithoutToken = buildTeam("", "name", "season")
        val newTeam = buildTeam("token", "name", "season")
        //When
        whenever(mockUserRepo.addTeam(user, teamWithoutToken)).thenReturn(newTeam)
        val teamReturn = sut.createTeam(user, teamWithoutToken)
        //Then
        verify(mockUserRepo).addTeam(user, teamWithoutToken)
        assertEquals(teamReturn, newTeam)
    }

    @Test
    fun `should reject creation team when user has already created a team with the same name and season`() {
        //Given
        val user = buildUser("mail", "password")
        val userTeam = buildTeam("token", "name", "season")
        user.teams = listOf(userTeam)
        val newTeam = buildTeam("", "name", "season")
        //When
        assertThrows<TeamAlreadyExists> {
            sut.createTeam(user, newTeam)
        }
    }

    @Test
    fun `should find existing team`() {
        //Given
        val team = buildTeam("token", "name", "season")
        //When
        whenever(mockTeamRepo.findByToken("token")).thenReturn(team)
        val teamReturn = sut.findTeamByToken("token")
        //Then
        verify(mockTeamRepo).findByToken("token")
        assertEquals(teamReturn, team)
    }

    @Test
    fun `should join existing team`() {
        //Given
        val team = buildTeam("token", "name", "season")
        val player = buildPlayer("mail")
        val teamWithNewPlayer = buildTeam("token", "name", "season")
        teamWithNewPlayer.players = listOf(player)
        //When
        whenever(mockTeamRepo.addPlayer(team, player)).thenReturn(teamWithNewPlayer)
        val teamReturn = sut.joinTeam(team, player)
        //Then
        verify(mockTeamRepo).addPlayer(team, player)
        assertTrue(teamReturn?.players!!.contains(player))

    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firstname", "lastname")
    }

    private fun buildUserTeam(mail: String): UserTeam {
        return UserTeam(
            contact = buildContact(mail)
        )
    }

    private fun buildPlayer(mail : String): UserTeam {
        return UserTeam(
            contact = buildContact(mail),
            playerInfo = PlayerInfo(
                strongFoot = PlayerFoot.BOTH,
                originalPosition = PlayerPosition.GK)
        )

    }

    private fun buildUser(mail: String, password: String): User {
        return User(
            userTeam = buildUserTeam(mail),
            password = password
        )
    }

    private fun buildTeam(token: String, name: String, season: String): Team {
        return Team(
            token = token,
            name = name,
            season = season,
            creationDate = LocalDateTime.now(),
            licenceAmount = 0.0f,
            homeStadium = Stadium("name", Address("", "", "")),
            president = buildUserTeam("mail")
        )
    }

}