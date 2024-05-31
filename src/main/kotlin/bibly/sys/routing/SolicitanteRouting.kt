package bibly.sys.routing

import bibly.sys.tables.Solicitante
import bibly.sys.repository.SolicitanteRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringSolicitanteRouting(){
    var endpoint = "/solicitante"
    var repository = SolicitanteRepository()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var solicitante = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = solicitante)
        }

        post(endpoint){
            var solicitante = call.receive<Solicitante>()
            repository.insert(solicitante)
            call.respond(HttpStatusCode.OK)
        }

        put("$endpoint/{id}"){
            var solicitante = call.receive<Solicitante>()
            repository.update(call.parameters["id"]!!.toInt(), solicitante)
            call.respond(HttpStatusCode.OK)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}
