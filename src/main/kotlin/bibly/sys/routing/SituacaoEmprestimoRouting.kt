package bibly.sys.routing

import bibly.sys.tables.SituacaoReserva
import bibly.sys.repository.SituacaoReservaRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringSituacaoReservaRouting(){
    var endpoint = "/situacao-emprestimo"
    var repository = SituacaoReservaRepository()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var reserva = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = reserva)
        }

        post(endpoint){
            var reserva = call.receive<SituacaoReserva>()
            repository.insert(reserva)
            call.respond(HttpStatusCode.OK)
        }

        put("$endpoint/{id}"){
            var reserva = call.receive<SituacaoReserva>()
            repository.update(call.parameters["id"]!!.toInt(), reserva)
            call.respond(HttpStatusCode.OK)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}

