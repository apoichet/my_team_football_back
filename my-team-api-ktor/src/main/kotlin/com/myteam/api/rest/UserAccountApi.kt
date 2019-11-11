package com.myteam.api.rest

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

fun Route.userAccount(logger: Logger) {

    val dataSourceName = "my_team_pu"
    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory(dataSourceName)
    val em: EntityManager = emf.createEntityManager()

    val userRepo = UserRepositoryImpl(em)
    val teamRepo = TeamRepositoryImpl(em)
    val userAccount = UserAccount(userRepo, teamRepo)

    post("user/create") {
        val newUser = call.receive<User>()
        userAccount.createAccount(newUser)?.let {
            logger.info("User ${newUser.contact.mail} created with success")
            call.respond(HttpStatusCode.Created, it)
        } ?: call.respond(HttpStatusCode.Accepted)
    }
}

