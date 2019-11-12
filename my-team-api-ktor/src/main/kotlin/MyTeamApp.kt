import com.fasterxml.jackson.core.util.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.myteam.api.rest.*
import com.myteam.core.exception.*
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


fun Application.main() {

    val logger = LoggerFactory.getLogger("Application")
    val dataSource = getDataSource("my_team_pu")

    install(DefaultHeaders)
    install(Compression)
    routing(logger, dataSource)
    installMapper()
    manageException(logger)
}

private fun Application.routing(
    logger: Logger,
    dataSource: EntityManager
) {
    install(Routing) {
        route("/myteam") {
            userRegister(logger, dataSource)
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

fun main(args: Array<String>) {
    embeddedServer(Netty, 8080, watchPaths = listOf("my-team-api-ktor"), module = Application::main).start()
}





