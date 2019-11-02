package com.myteam.repository.jpa.impl

import com.myteam.core.domain.*
import com.myteam.core.enums.PlayerFoot
import com.myteam.core.enums.PlayerPosition
import com.myteam.repository.jpa.adapter.TeamAdapter
import org.junit.jupiter.api.Test
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
            name = "name",
            president = buildTeamMember(mailPresident),
            homeStadium = buildStadium()
        )
    }

    private fun buildTeamMember(mail: String): TeamMember{
        return TeamMember(
            contact = buildContact(mail)
        )
    }

    private fun buildPlayer(mail: String): Player{
        return Player(
            contact = buildContact(mail),
            originalPosition = PlayerPosition.GK,
            strongFoot = PlayerFoot.RIGHT
        )
    }

    private fun buildStadium(): Stadium {
        return Stadium(name = "name", address = buildAddress("2 rue du stade"))
    }

    private fun buildAddress(address: String): Address {
        return Address(address , "city", "zip_code", "country")
    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firtsname", "lastname")
    }





}