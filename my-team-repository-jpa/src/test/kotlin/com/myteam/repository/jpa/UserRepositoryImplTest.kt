package com.myteam.repository.jpa

import com.myteam.core.domain.*
import com.myteam.core.enums.PlayerFoot
import com.myteam.core.enums.PlayerPosition
import com.myteam.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class UserRepositoryImplTest {

    private lateinit var sut: UserRepository

    @BeforeEach
    fun setUp() {
        sut = UserRepositoryImpl("my_team_pu_test")
    }

    @Test
    fun `should find a new user in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        val address1 = buildAddress("2 rue Pleyel")
        val address2 = buildAddress("3 rue du stade")
        val addresses = listOf(address1, address2)
        //When
        sut.create(newUser)
        val userFound = sut.find(1)
        //Then
        assertEquals(userFound?.id, newUser.id)
    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firtsname", "lastname")
    }

    private fun buildAddress(address: String): Address {
        return Address(address , "city", "zip_code", "country")
    }

    private fun buildUser(mail: String, password: String): User {
        return User(
            id = 1,
            contact = buildContact(mail),
            password = password
        )
    }

    private fun buildTeam(token: String): Team {
        return Team(
            token = token,
            name = "name",
            creationDate = LocalDateTime.now(),
            licenceAmount = 0.0f,
            homeStadium = Stadium("name", Address("", "", "")),
            president = buildTeamMember("mail")
        )
    }

    private fun buildPlayer(mail : String): Player {
        return Player(
            contact = buildContact(mail),
            strongFoot = PlayerFoot.BOTH,
            positions = emptyList(),
            originalPosition = PlayerPosition.GK
        )

    }

    private fun buildTeamMember(mail: String): TeamMember {
        return TeamMember(contact = buildContact(mail))

    }

}


