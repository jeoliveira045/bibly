package bibly.sys.routing

import bibly.sys.tables.Cliente
import bibly.sys.repository.ClienteRepository
import bibly.sys.services.CadastrarClienteService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringClienteRouting(){
    var endpoint = "/cliente"
    var repository = ClienteRepository()
    var cadastrarClienteService = CadastrarClienteService()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var cliente = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = cliente)
        }

        post(endpoint){
            var cliente = call.receive<Cliente>()
            cadastrarClienteService.exec(cliente)
            repository.insert(cliente)
            call.respond(HttpStatusCode.OK)
        }

        put("$endpoint/{id}"){
            var cliente = call.receive<Cliente>()
            repository.update(call.parameters["id"]!!.toInt(), cliente)
            call.respond(HttpStatusCode.OK)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}
