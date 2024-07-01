package bibly.sys.tables

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.kotlin.datetime.date


@Serializable
open class Solicitante(
    var id: Int?,
    var nome: String,
    var sobrenome: String,
    var datanascimento: LocalDate,
    var genero: String,
    var endereco: String,
    var cpf: String
)

object Solicitantes: IntIdTable(){
    var nome = varchar("nome", 50)
    var sobrenome = varchar("sobrenome", 50)
    var datanascimento = date("datanascimento")
    var genero = varchar("genero", 50)
    var endereco = varchar("endereco", 50)
    var cpf = varchar("cpf", 50)
}
class SolicitanteDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<SolicitanteDAO>(Solicitantes)
    var nome by Solicitantes.nome
    var sobrenome by Solicitantes.sobrenome
    var datanascimento by Solicitantes.datanascimento
    var genero by Solicitantes.genero
    var endereco by Solicitantes.endereco
    var cpf by Solicitantes.cpf
}
