package bibly.sys.tables

import bibly.sys.plugins.tables.Livros
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date
import javax.swing.text.html.parser.Entity

@Serializable
data class Reserva(
    val id: Int?,
    val dataReserva: LocalDate,
    val dataEmprestimoEm: LocalDate,
    val prazoDevolucao: LocalDate,
    val cliente_id: Int,
    val livro_id: Int
)

object Reservas: IntIdTable(){
    val dataReserva = date("dataReserva")
    val dataEmprestimoEm = date("dataEmprestimoEm")
    val prazoDevolucao = date("prazoDevolucao")
    val cliente_id = integer("cliente_id").references(Clientes.id)
    val livro_id = integer("livro_id").references(Livros.id)
}

class ReservaDAO(id: EntityID<Int>): IntEntity(id){
    companion object : IntEntityClass<ReservaDAO>(Reservas)
    var dataReserva by Reservas.dataReserva
    var dataEmprestimoEm by Reservas.dataEmprestimoEm
    var prazoDevolucao by Reservas.prazoDevolucao
    var cliente_id by Reservas.cliente_id
    var livro_id by Reservas.livro_id
}


