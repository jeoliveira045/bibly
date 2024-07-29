package bibly.sys.tables

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date


@Serializable
data class Cliente(
    var id: Int?,
    var nome: String,
    var sobrenome: String,
    var datanascimento: LocalDate,
    var genero: String,
    var endereco: String,
    var cpf: String
)

object Clientes : IntIdTable(){
    val nome = varchar("nome", 50)
    val sobrenome = varchar("sobrenome", 50)
    val datanascimento = date("datanascimento")
    val genero = varchar("genero", 50)
    val endereco = varchar("endereco", 50)
    var cpf = varchar("cpf", 50)

}

class ClienteDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<ClienteDAO>(Clientes)
    var nome by Clientes.nome
    var sobrenome by Clientes.sobrenome
    var datanascimento by Clientes.datanascimento
    var genero by Clientes.genero
    var endereco by Clientes.endereco
    var cpf by Clientes.cpf
}
