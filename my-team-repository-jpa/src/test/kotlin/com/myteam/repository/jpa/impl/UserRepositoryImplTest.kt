package com.myteam.repository.jpa.impl

import com.myteam.core.domain.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.DriverManager
import java.sql.SQLException
import java.util.*
import java.io.BufferedReader
import java.io.FileReader


internal class UserRepositoryImplTest: RepositoryImplTest() {

    private val sut = UserRepositoryImpl("my_team_pu_test")

    @Test
    fun `should find a new user`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        val userCreated = sut.create(newUser)
        val userFound = sut.findByMail(userCreated.contact.mail)
        //Then
        assertEquals(userFound?.contact?.mail, userCreated.contact.mail)
    }

    @Test
    fun `should not find unknown user`() {
        //Given
        val userUnknown = buildUser("unknown_mail", "mdp")
        //When
        val userFound = sut.findByMail(userUnknown.contact.mail)
        //Then
        assertNull(userFound)
    }

    @Test
    fun `should update user mail`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        sut.create(newUser)
        val userUpdated = sut.updateContact(newUser,  buildContact("new_mail"))
        //Then
        assertEquals(userUpdated.contact.mail, "new_mail")
    }

    @Test
    fun `should update user password`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        sut.create(newUser)
        val userUpdated = sut.updatePassword(newUser, "new_mdp")
        //Then
        assertEquals(userUpdated.password, "new_mdp")
    }

    @Test
    fun `should update user addresses`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        val address1 = buildAddress("2 rue Pleyel")
        val address2 = buildAddress("3 rue du stade")
        val addresses = listOf(address1, address2)
        newUser.contact.addresses = addresses
        val userToModified = sut.create(newUser)
        //When
        val newContact = buildContact("mail4")
        address1.address = "3 rue Piochet"
        newContact.addresses = addresses
        val userUpdated = sut.updateContact(userToModified, newContact)
        //Then
        assertEquals(userUpdated.contact.addresses.first().address, "3 rue Piochet")
    }

    @Test
    fun `should delete user`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        //When
        val userCreated = sut.create(newUser)
        val deleted = sut.delete(userCreated)
        //Then
        assertTrue(deleted)
    }

    @Test
    fun `should delete user with addresses`() {
        //Given
        val newUser = buildUser("mail", "mdp")
        val address1 = buildAddress("2 rue Pleyel")
        val address2 = buildAddress("3 rue du stade")
        val addresses = listOf(address1, address2)
        newUser.contact.addresses = addresses
        //When
        val userCreated = sut.create(newUser)
        val deleted = sut.delete(userCreated)
        //Then
        assertTrue(deleted)
    }

    private fun buildContact(mail: String): Contact {
        return Contact(mail, "firtsname", "lastname")
    }

    private fun buildAddress(address: String): Address {
        return Address(address , "city", "zip_code", "country")
    }

    private fun buildUser(mail: String, password: String): User {
        return User(
            contact = buildContact(mail),
            password = password
        )
    }

}


