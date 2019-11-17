package com.myteam.api.rest

import com.myteam.application.*
import com.myteam.core.domain.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.slf4j.*

fun Route.userProfile(logger: Logger, userProfile: UserProfile?) {

    route("/user/profile") {

        delete("close") {
            val user = call.receive<User>()
            if(userProfile!!.closeAccount(user)) {
                logger.info("Close user account with mail '${user.contact.mail}'")
                call.respond(HttpStatusCode.OK)
            }
            else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        route("/update") {

            patch("password") {
                val receive = call.receive<ModifyPasswordWrapper>()
                userProfile!!.modifyPassword(receive.user, receive.newPassword)?.let {
                    logger.info("User with mail '${receive.user.contact.mail}' change his password")
                    call.respond(HttpStatusCode.OK, it)
                }
            }

        }


    }


}

class ModifyPasswordWrapper(val user: User, val newPassword: String)