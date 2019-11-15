import com.fasterxml.jackson.core.util.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.myteam.api.rest.*
import com.myteam.application.*
import com.myteam.core.exception.*
import com.myteam.core.usecase.*
import com.myteam.infra.*
import com.myteam.repository.jpa.impl.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.*
import java.lang.Exception
import javax.persistence.*


fun Application.mainWithDependencies(
    userRegister: UserRegister? = null,
    userLogin: UserLogin? = null
) {
    install(DefaultHeaders)
    install(Compression)
    installMapper()

    val logger = LoggerFactory.getLogger("Application")
    manageException(logger)

    install(Routing) {
        route("/myteam") {
            userRegister(logger, userRegister)
            userLogin(userLogin)
        }
    }


}

private fun Application.manageException(logger: Logger) {
    install(StatusPages) {
        exception<Exception> { cause ->
            var httpCodeResponse = HttpStatusCode.InternalServerError
            when (cause) {
                is UserMailAlreadyExist -> {
                    httpCodeResponse = HttpStatusCode.Conflict
                }
            }
            logger.error(cause.message)
            call.respond(httpCodeResponse, cause.message ?: "")
        }
    }
}

private fun Application.installMapper() {
    install(ContentNegotiation) {
        register(ContentType.Application.Json, JacksonConverter())
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                indentArraysWith(DefaultPrettyPrinter.FixedSpaceIndenter.instance)
                indentObjectsWith(DefaultIndenter("  ", "\n"))
            })
            registerModule(JavaTimeModule())
        }
    }
}

fun getDataSource(dataSourceName: String): EntityManager {
    val emf: EntityManagerFactory = Persistence.createEntityManagerFactory(dataSourceName)
    return emf.createEntityManager()
}

fun Application.main() {
    val dataSource = getDataSource("my_team_pu")

    val userRepo: UserRepository = UserRepositoryImpl(dataSource)
    val teamRepo: TeamRepository = TeamRepositoryImpl(dataSource)

    val userRegister: UserRegister = UserAccount(userRepo, teamRepo)
    val userLogin: UserLogin = UserAccount(userRepo, teamRepo)

    mainWithDependencies(userRegister, userLogin)
}

fun main() {
    embeddedServer(Netty, 8080, watchPaths = listOf("my-team-api-ktor"), module = Application::main).start()
}





