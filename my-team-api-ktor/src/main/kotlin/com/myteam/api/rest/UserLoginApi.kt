package com.myteam.api.rest

import com.myteam.application.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.userLogin(userLogin: UserLogin?) {

    post("user/login") {
        userLogin!!.loginUser("mail", "password")?.let {
            call.respond(HttpStatusCode.OK, it)
        } ?: call.respond(HttpStatusCode.NotFound)
    }
}