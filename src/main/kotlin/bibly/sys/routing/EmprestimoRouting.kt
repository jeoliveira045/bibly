package bibly.sys.routing

import bibly.sys.repository.EmprestimoRepository
import bibly.sys.tables.Emprestimo
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringEmprestimoRouting(){
    var endpoint = "/emprestimo"
    var repository = EmprestimoRepository()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var emprestimo = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = emprestimo)
        }

        post(endpoint){
            var emprestimo = call.receive<Emprestimo>()
            repository.insert(emprestimo)
            call.respond(HttpStatusCode.OK)
        }

        put("$endpoint/{id}"){
            var emprestimo = call.receive<Emprestimo>()
            repository.update(call.parameters["id"]!!.toInt(), emprestimo)
            call.respond(HttpStatusCode.OK)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
            call.respond(HttpStatusCode.OK)
        }
    }
}
