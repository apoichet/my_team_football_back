package com.myteam.api.rest

import JsonMapper
import com.myteam.application.*
import com.myteam.core.domain.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
import io.mockk.impl.annotations.*
import io.mockk.junit5.*
import mainWithDependencies
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.*
import kotlin.test.*


@ExtendWith(MockKExtension::class)
internal class UserLoginApiTest {

    @RelaxedMockK
    private lateinit var mockUserLogin: UserLogin
    private val mapper = JsonMapper.defaultMapper

    @Test
    fun `should respond 200 with user login`() = testApp {
        val existingUser = User(password = "password", userTeam =  UserTeam(firstName = "firstName", lastName = "lastName", contact = Contact(mail = "mail")))
        every { mockUserLogin.loginUser(any(), any()) } returns existingUser
        handleRequest ( HttpMethod.Post, "/myteam/user/login" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody("""{"password" : "password", "mail" : "mail"}""")
        }.apply {
            assertEquals(HttpStatusCode.OK, this.response.status())
            val userLog = mapper.readValue(this.response.content, User::class.java)
            assertEquals("mail", userLog.userTeam.contact.mail)
        }
    }

    @Test
    fun `should respond 404 with user unknown`() = testApp {
        every { mockUserLogin.loginUser(any(), any()) } returns null
        handleRequest ( HttpMethod.Post, "/myteam/user/login" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody("""{"password" : "password", "mail" : "mail"}""")
        }.apply {
            assertEquals(HttpStatusCode.NotFound, this.response.status())
            assertNull(this.response.content)
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(userLogin = mockUserLogin) }) { callback() }
    }

}