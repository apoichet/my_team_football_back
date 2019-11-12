package com.myteam.api.rest

import com.myteam.application.*
import com.myteam.core.domain.*
import com.myteam.core.usecase.*
import com.myteam.repository.jpa.impl.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.slf4j.*
import javax.persistence.*

fun Route.userRegister(logger: Logger, dataSource: EntityManager) {

    val userRepo = UserRepositoryImpl(dataSource)
    val teamRepo = TeamRepositoryImpl(dataSource)
    val userRegister: UserRegister = UserAccount(userRepo, teamRepo)

    post("user/create") {
        val newUser = call.receive<User>()
        userRegister.createAccount(newUser)?.let {
            logger.info("User ${newUser.contact.mail} created with success")
            call.respond(HttpStatusCode.Created, it)
        } ?: call.respond(HttpStatusCode.Accepted)
    }
}

