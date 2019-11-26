package com.myteam.api.rest

import com.myteam.application.*
import com.myteam.core.domain.*
import com.myteam.core.exception.*
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
                logger.info("Close user account with mail '${user.userTeam.contact.mail}'")
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
                    logger.info("User with mail '${receive.user.userTeam.contact.mail}' modify password")
                    call.respond(HttpStatusCode.OK, it)
                }
            }

            patch("contact") {
                val receive = call.receive<ModifyContactWrapper>()
                try {
                    userProfile!!.modifyContact(receive.user, receive.newContact)?.let {
                        logger.info("User with mail '${receive.user.userTeam.contact.mail}' modify contact")
                        call.respond(HttpStatusCode.OK, it)
                    }
                }
                catch(e: UserMailAlreadyExist) {
                    call.respond(HttpStatusCode.Conflict)
                }
            }
        }
    }
}

class ModifyPasswordWrapper(val user: User, val newPassword: String)

class ModifyContactWrapper(val user: User, val newContact: Contact)