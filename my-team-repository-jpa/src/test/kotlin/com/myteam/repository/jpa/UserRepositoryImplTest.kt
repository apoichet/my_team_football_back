package com.myteam.repository.jpa

import com.myteam.core.domain.*
import com.myteam.core.enums.PlayerFoot
import com.myteam.core.enums.PlayerPosition
import com.myteam.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import java.time.LocalDateTime
import org.junit.jupiter.api.assertThrows

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
        //When
        sut.create(newUser)
        val userFound = sut.find(1)
        //Then
        assertEquals(userFound?.id, newUser.id)
    }

    @Test
    fun `should reject when try to find unknown user in data base`() {
        //When
        assertThrows<IllegalStateException> {
            sut.find(0)
        }
    }

    @Test
    fun `should update user mail in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        sut.create(newUser)
        newUser.contact.mail = "new_mail"
        val userUpdated = sut.update(newUser)
        //Then
        assertEquals(userUpdated.contact.mail, "new_mail")
    }

    @Test
    fun `should update user password in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        sut.create(newUser)
        newUser.password = "new_mdp"
        val userUpdated = sut.update(newUser)
        //Then
        assertEquals(userUpdated.password, "new_mdp")
    }

    @Test
    fun `should update user addresses in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        val address1 = buildAddress("2 rue Pleyel")
        val address2 = buildAddress("3 rue du stade")
        val addresses = listOf(address1, address2)
        newUser.contact.addresses = addresses
        //When
        sut.create(newUser)
        newUser.contact.addresses.first().city = "Bordeaux"
        val userUpdated = sut.update(newUser)
        //Then
        assertEquals(userUpdated.contact.addresses.first().city, "Bordeaux")
    }

    @Test
    fun `should add new team to user in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        sut.create(newUser)
        newUser.teams = listOf(buildTeam("token"))
        val userUpdated = sut.update(newUser)
        //Then
        assertEquals(userUpdated.teams.first().token, "token")
    }

    @Test
    fun `should delete user in data base`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        val userCreated = sut.create(newUser)
        val deleted = sut.delete(userCreated)
        val userFound = sut.find(userCreated.id)
        //Then
        assertTrue(deleted)
        assertNull(userFound)
    }

    @Test
    fun `should reject when try to delete unknown user in data base`() {
        //When
        assertThrows<IllegalStateException> {
            sut.delete(buildUser("mail", "mdp"))
        }
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


