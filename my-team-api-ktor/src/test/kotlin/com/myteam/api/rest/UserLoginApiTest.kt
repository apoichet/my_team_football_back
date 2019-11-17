package com.myteam.api.rest

import com.fasterxml.jackson.module.kotlin.*
import com.myteam.application.*
import com.myteam.core.domain.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
import io.mockk.impl.annotations.*
import io.mockk.junit5.*
import mainWithDependencies
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*


@ExtendWith(MockKExtension::class)
internal class UserLoginApiTest {

    @RelaxedMockK
    private lateinit var mockUserLogin: UserLogin
    private val mapper = JsonMapper.defaultMapper

    @Test
    fun `should respond with 200 with user login`() = testApp {
        val existingUser = User(password = "password", contact = Contact(firstName = "firstName", lastName = "lastName", mail = "mail"))
        every { mockUserLogin.loginUser(any(), any()) } returns existingUser
        handleRequest ( HttpMethod.Post, "/myteam/user/login" ) {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody("""{"password" : "password", "mail" : "mail"}""")
        }.apply {
            Assertions.assertEquals(HttpStatusCode.OK, this.response.status())
            val userLog = mapper.readValue(this.response.content, User::class.java)
            Assertions.assertEquals("mail", userLog.contact.mail)
        }
    }

    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(userLogin = mockUserLogin) }) { callback() }
    }

}