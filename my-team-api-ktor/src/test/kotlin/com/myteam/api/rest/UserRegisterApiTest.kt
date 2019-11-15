package com.myteam.api.rest

import com.myteam.application.*
import com.myteam.core.domain.*
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
internal class UserRegisterApiTest {

    @RelaxedMockK
    lateinit var mockUserRegister: UserRegister

    @Test
    fun `should create new user`() = testApp {
        val newUser = User(password = "password", contact = Contact(mail = "a.poichet1@gmail.com", firstName = "Alexandre", lastName = "Alexandre"))
        every { mockUserRegister.createAccount(newUser) } returns newUser
        handleRequest(HttpMethod.Post, "/myteam/user/create") {
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody("""{"password":"password","contact":{"firstName":"Alexandre", "lastName":"Poichet", "mail": "a.poichet1@gmail.com"}}""")
        }.apply {
            assertEquals(HttpStatusCode.Created, this.response.status())
            assertNotNull(this.response.content)
        }
    }

    /**
     * Private method used to reduce boilerplate when testing the application.
     */
    private fun testApp(callback: TestApplicationEngine.() -> Unit) {
        withTestApplication({ mainWithDependencies(mockUserRegister) }) { callback() }
    }

}