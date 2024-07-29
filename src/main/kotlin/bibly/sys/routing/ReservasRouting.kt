package bibly.sys.routing

import bibly.sys.tables.Reserva
import bibly.sys.repository.ReservaRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringReservaRouting(){
    var endpoint = "/reserva"
    var repository = ReservaRepository()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var reserva = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = reserva)
        }

        post(endpoint){
            var reserva = call.receive<Reserva>()
            repository.insert(reserva)
            call.respond(HttpStatusCode.OK)
        }

        put("$endpoint/{id}"){
            var reserva = call.receive<Reserva>()
            repository.update(call.parameters["id"]!!.toInt(), reserva)
            call.respond(HttpStatusCode.OK)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}

