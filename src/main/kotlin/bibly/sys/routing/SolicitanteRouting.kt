package bibly.sys.routing

import bibly.sys.plugins.tables.Solicitante
import bibly.sys.repository.SolicitanteRepository
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
        }

        put("$endpoint/{id}"){
            var solicitante = call.receive<Solicitante>()
            repository.update(call.parameters["id"]!!.toInt(), solicitante)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
        }
    }
}
