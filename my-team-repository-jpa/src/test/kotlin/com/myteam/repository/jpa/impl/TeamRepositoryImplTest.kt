package com.myteam.repository.jpa.impl

import com.myteam.core.domain.*
import com.myteam.core.enums.*
import com.myteam.repository.jpa.adapter.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

internal class TeamRepositoryImplTest : RepositoryImplTest() {

    private val sut = TeamRepositoryImpl(super.em)
    private val teamAdapter = TeamAdapter()

    @Test
    fun `should find team with token`() {
        //Given
        val team = buildTeam("token", "mailPresident")
        em.persist(teamAdapter.convertDomainObjectToData(team))
        //When
        val teamFound = sut.findByToken(team.token)
        //Then
        assertEquals(teamFound?.token, team.token)
    }

    @Test
    fun `should not find team with unknown token`() {
        //When
        val teamUnknown = sut.findByToken("unknown_token")
        //Then
        assertNull(teamUnknown)
    }

    @Test
    fun `should add new player to team`() {
        //Given
        val team = buildTeam("token", "mailPresident")
        em.persist(teamAdapter.convertDomainObjectToData(team))
        //When
        val player = buildPlayer("mailPlayer")
        val teamWithNewPlayer = sut.addPlayer(team, player)
        val teamFound = sut.findByToken(teamWithNewPlayer.token)
        //Then
        assertEquals(teamFound?.players?.first()?.contact?.mail, player.contact.mail)
    }

    @Test
    fun `should reject when try to add new player to unknown team`() {
        //Given
        val unknownTeam = buildTeam("unknown_token", "mailPresident")
        //When
        val player = buildPlayer("mailPlayer")
        assertThrows(IllegalStateException::class.java) {
            sut.addPlayer(unknownTeam, player)
        }
    }

    private fun buildTeam(token: String, mailPresident: String): Team {
        return Team(
            token = token,
            season = "season",
            name = "name",
            president = buildUserTeam(mailPresident),
            homeStadium = buildStadium()
        )
    }

    private fun buildUserTeam(mail: String): UserTeam{
        return UserTeam(
            contact = buildContact(mail),
            firstName = "firtsname",
            lastName = "lastname"
        )
    }

    private fun buildPlayer(mail: String): UserTeam{
        return UserTeam(
            contact = buildContact(mail),
            firstName = "firtsName",
            lastName = "lastName",
            playerInfo = PlayerInfo(strongFoot = PlayerFoot.BOTH, originalPosition = PlayerPosition.GK)
        )
    }

    private fun buildStadium(): Stadium {
        return Stadium(name = "name", address = buildAddress("2 rue du stade"))
    }

    private fun buildAddress(address: String): Address {
        return Address(address , "city", "zip_code", "country")
    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail)
    }





}