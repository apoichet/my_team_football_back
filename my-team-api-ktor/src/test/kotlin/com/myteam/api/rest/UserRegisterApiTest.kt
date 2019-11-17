package com.myteam.api.rest

import JsonMapper
import com.fasterxml.jackson.module.kotlin.*
import com.myteam.application.*
import com.myteam.core.domain.*
import com.myteam.core.exception.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.AutoHeadResponse.install
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.server.testing.*
import io.mockk.*
import io.mockk.impl.annotations.*
import io.mockk.junit5.*
import mainWithDependencies
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*


@ExtendWith(MockKExtension::class)
internal class UserRegisterApiTest {

    @RelaxedMockK
    private lateinit var mockUserRegister: UserRegister
    private val mapper = JsonMapper.defaultMapper
    private val newUserPath = "/basic_new_user.json"

    @Test
    fun `should respond with 201 when create new user`() = testApp {
        val newUserJson =  this.javaClass.getResource(newUserPath).readText(Charsets.UTF_8)
        val newUser = mapper.readValue<User>(newUserJson)
        every { mockUserRegister.createAccount(any()) } returns newUser
        handleRequest(HttpMethod.Post, "/myteam/user/create") {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(newUserJson)
        }.apply {
            assertEquals(HttpStatusCode.Created, this.response.status())
            assertNotNull(this.response.content)
        }
    }

    @Test
    fun `should respond with 202 when no response return`() = testApp {
        val newUserJson =  this.javaClass.getResource(newUserPath).readText(Charsets.UTF_8)
        every { mockUserRegister.createAccount(any()) } returns null
        handleRequest(HttpMethod.Post, "/myteam/user/create") {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(newUserJson)
        }.apply {
            assertEquals(HttpStatusCode.Accepted, this.response.status())
            assertNull(this.response.content)
        }
    }

    @Test
    fun `should respond with 409 when user mail already exists`() = testApp {
        val newUserJson =  this.javaClass.getResource(newUserPath).readText(Charsets.UTF_8)
        every { mockUserRegister.createAccount(any()) } throws(UserMailAlreadyExist("user mail already exists"))
        handleRequest(HttpMethod.Post, "/myteam/user/create") {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody(newUserJson)
        }.apply {
            assertEquals(HttpStatusCode.Conflict, this.response.status())
            assertNull(this.response.content)
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(mockUserRegister) }) { callback() }
    }

}