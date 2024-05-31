package bibly.sys.tables

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.Solicitantes
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class Emprestimo(
    var id: Int?,
    val dtEmprestimoEm: LocalDate,
    val prazoDevolucaoEm: LocalDate,
    val dataDevolucao: LocalDate,
    val solicitante_id: Int,
    val livro_id: Int
)

object Emprestimos: IntIdTable(){
    val dtEmprestimoEm = date("dtEmprestimoEm")
    val prazoDevolucaoEm = date("prazoDevolucaoEm")
    val dataDevolucao = date("dataDevolucao")
    val solicitante_id = integer("solicitante_id").references(Solicitantes.id)
    val livro_id = integer("livro_id").references(Livros.id)
}

class EmprestimoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<EmprestimoDAO>(Emprestimos)
    var dtEmprestimoEm by Emprestimos.dtEmprestimoEm
    var prazoDevolucaoEm by Emprestimos.prazoDevolucaoEm
    var dataDevolucao by Emprestimos.dataDevolucao
    var solicitante_id by Emprestimos.solicitante_id
    var livro_id by Emprestimos.livro_id
}
