package bibly.sys.plugins.tables

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.date


@Serializable
data class Solicitante(
    var id: Int,
    var nome: String,
    var sobrenome: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var endereco: String,
    var cpf: String
)

object Solicitantes : Table(){
    val id = integer("id").autoIncrement()
    val nome = varchar("nome", 50)
    val sobrenome = varchar("sobrenome", 50)
    val dataNascimento = date("dataNascimento")
    val genero = varchar("genero", 50)
    val endereco = varchar("endereco", 50)
    var cpf = varchar("cpf", 50)

    override val primaryKey = PrimaryKey(Solicitantes.id)

}
