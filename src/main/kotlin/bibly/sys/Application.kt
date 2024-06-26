package bibly.sys

import bibly.sys.plugins.*
import bibly.sys.routing.configuringEmprestimoRouting
import bibly.sys.routing.configuringLivroRouting
import bibly.sys.routing.configuringSolicitanteRouting
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation){
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    DatabaseConnection.init()
    configuringSolicitanteRouting()
    configuringLivroRouting()
    configuringEmprestimoRouting()
}
