package com.myteam.api.rest

import JsonMapper
import com.fasterxml.jackson.module.kotlin.*
import com.myteam.application.*
import com.myteam.core.domain.*
import com.myteam.core.exception.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
import io.mockk.impl.annotations.*
import io.mockk.junit5.*
import mainWithDependencies
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*

@ExtendWith(MockKExtension::class)
internal class UserProfileApiTest {

    @RelaxedMockK
    private lateinit var mockUserProfile: UserProfile
    private val mapper = JsonMapper.defaultMapper

    @Test
    fun `should respond 200 when user close account`() = testApp {
        val existingUser = this.javaClass.getResource("/basic_user.json").readText(Charsets.UTF_8)
        every { mockUserProfile.closeAccount(any()) } returns true
        handleRequest ( HttpMethod.Delete, "/myteam/user/profile/close" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(existingUser)
        }.apply {
            assertEquals(HttpStatusCode.OK, this.response.status())
        }
    }

    @Test
    fun `should respond 404 when not close account`() = testApp {
        val existingUser = this.javaClass.getResource("/basic_user.json").readText(Charsets.UTF_8)
        every { mockUserProfile.closeAccount(any()) } returns false
        handleRequest ( HttpMethod.Delete, "/myteam/user/profile/close" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(existingUser)
        }.apply {
            assertEquals(HttpStatusCode.NotFound, this.response.status())
        }
    }

    @Test
    fun `should respond 200 when modify password`() = testApp {
        val userWithNewPasswordJson = this.javaClass.getResource("/user_with_new_password.json").readText(Charsets.UTF_8)
        val userWithNewPassword = mapper.readValue<ModifyPasswordWrapper>(userWithNewPasswordJson)
        val user = userWithNewPassword.user
        user.password = userWithNewPassword.newPassword
        every { mockUserProfile.modifyPassword(any(), any()) } returns user
        handleRequest ( HttpMethod.Patch, "/myteam/user/profile/update/password" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(userWithNewPasswordJson)
        }.apply {
            assertEquals(HttpStatusCode.OK, this.response.status())
            val userModified = mapper.readValue(this.response.content, User::class.java)
            assertEquals(userModified.password, user.password)
        }
    }

    @Test
    fun `should respond 200 when modify profile`() = testApp {
        val userWithNewProfileJson = this.javaClass.getResource("/user_with_new_contact.json").readText(Charsets.UTF_8)
        val userWithNewProfile = mapper.readValue<ModifyProfileWrapper>(userWithNewProfileJson)
        val user = userWithNewProfile.user
        user.userTeam = userWithNewProfile.newProfile
        every { mockUserProfile.modifyProfile(any(), any()) } returns user
        handleRequest ( HttpMethod.Patch, "/myteam/user/profile/update/profile" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(userWithNewProfileJson)
        }.apply {
            assertEquals(HttpStatusCode.OK, this.response.status())
            val userModified = mapper.readValue(this.response.content, User::class.java)
            assertEquals(userModified.userTeam.contact.mail, user.userTeam.contact.mail)
            assertEquals(userModified.userTeam.firstName, user.userTeam.firstName)
        }
    }

    @Test
    fun `should respond 409 when modify contact with mail already exists`() = testApp {
        val userWithNewProfileJson = this.javaClass.getResource("/user_with_new_contact.json").readText(Charsets.UTF_8)
        val userWithNewProfile = mapper.readValue<ModifyProfileWrapper>(userWithNewProfileJson)
        val user = userWithNewProfile.user
        user.userTeam = userWithNewProfile.newProfile
        every { mockUserProfile.modifyProfile(any(), any()) } throws UserMailAlreadyExist("user mail already exists")
        handleRequest ( HttpMethod.Patch, "/myteam/user/profile/update/profile" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(userWithNewProfileJson)
        }.apply {
            assertEquals(HttpStatusCode.Conflict, this.response.status())
            assertNull(this.response.content)
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(userProfile = mockUserProfile) }) { callback() }
    }



}