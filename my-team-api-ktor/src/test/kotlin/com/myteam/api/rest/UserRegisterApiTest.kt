package com.myteam.api.rest

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import main
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class UserRegisterApiTest {
    @Test
    fun testRequests() = withTestApplication(Application::main) {
        with(handleRequest(HttpMethod.Post, "/myteam/user/create"){
            addHeader("Accept", "application/json")
            addHeader("Content-Type", "application/json")
            setBody("""{"password":"password","contact":{"firstName":"Alexandre", "lastName":"Poichet", "mail": "a.poichet1@gmail.com"}}""")
        }) {
            assertEquals(HttpStatusCode.Created, this.response.status())
            assertNotNull(this.response.content)
        }

    }

}