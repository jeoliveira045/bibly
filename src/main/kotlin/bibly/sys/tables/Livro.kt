package bibly.sys.plugins.tables

import bibly.sys.tables.Clientes
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import java.util.Objects

@Serializable
data class Livro(
    var id: Int?,
    var nome: String,
    var isbn: String,
    var genero: String,
    var autor: String
)

object Livros: IntIdTable() {
    val nome = varchar("nome", 50)
    val isbn = varchar("isbn", 50)
    val genero = varchar("genero", 50)
    val autor = varchar("autor", 50)

}

class LivroDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<LivroDAO>(Livros)
    var nome by Livros.nome
    var isbn by Livros.isbn
    var autor by Livros.autor
    var genero by Livros.genero
}

