package bibly.sys

import bibly.sys.plugins.*
import bibly.sys.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


class MyClass {
    var myField: String = "Hello, Reflection!"
}
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
    configuringClienteRouting()
    configuringSituacaoEmprestimoRouting()
    configuringSituacaoReservaRouting()
    configuringLivroRouting()
    configuringEmprestimoRouting()
    configuringReservaRouting()
}






