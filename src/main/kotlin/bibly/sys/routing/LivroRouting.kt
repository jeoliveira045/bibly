package bibly.sys.routing

import bibly.sys.plugins.tables.Livro
import bibly.sys.repository.EmprestimoRepository
import bibly.sys.repository.LivroRepository
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configuringLivroRouting(){
    var endpoint = "/livro"
    var repository = LivroRepository()

    routing {
        get(endpoint){
            call.respond(repository.findAll());
        }

        get("$endpoint/{id}"){
            var livro = repository.findById(call.parameters["id"]!!.toInt())
            call.respond(message = livro)
        }

        post(endpoint){
            var livro = call.receive<Livro>()
            repository.insert(livro)
        }

        put("$endpoint/{id}"){
            var livro = call.receive<Livro>()
            repository.update(call.parameters["id"]!!.toInt(), livro)
        }

        delete("$endpoint/{id}"){
            repository.delete(call.parameters["id"]!!.toInt())
        }
    }
}
