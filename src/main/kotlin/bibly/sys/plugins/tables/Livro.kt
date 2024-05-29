package bibly.sys.plugins.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class Livro(
    var id: Long,
    var nome: String,
    var ISBN: String,
    var genero: String,
    var autor: String
)

object Livros: Table() {
    val id = integer("id").autoIncrement()
    val nome = varchar("nomeLivro", 50)
    val isbn = varchar("isbn", 50)
    val genero = varchar("genero", 50)
    val autor = varchar("autor", 50)

    override val primaryKey = PrimaryKey(id)
}
