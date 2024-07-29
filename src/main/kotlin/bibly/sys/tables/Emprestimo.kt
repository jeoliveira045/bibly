package bibly.sys.tables

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.LivroDAO
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.Clientes
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.View
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Function
import org.jetbrains.exposed.sql.Select
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class Emprestimo(
    var id: Int?,
    var dtEmprestimoEm: LocalDate,
    var prazoDevolucaoEm: LocalDate,
    var dataDevolucao: LocalDate,
    var cliente_id: Int,
    var livros: List<Livro>,
    var situacaoemprestimo_id: Int?
)

object Emprestimos: IntIdTable() {
    var dtEmprestimoEm = date("dtemprestimoem")
    var prazoDevolucaoEm = date("prazodevolucaoem")
    var dataDevolucao = date("datadevolucao")
    var cliente_id = integer("cliente_id").references(Clientes.id)
    val situacaoemprestimo_id = integer("situacaoemprestimo_id").references(SituacaoEmprestimos.id)
}



class EmprestimoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<EmprestimoDAO>(Emprestimos)
    var dtEmprestimoEm by Emprestimos.dtEmprestimoEm
    var prazoDevolucaoEm by Emprestimos.prazoDevolucaoEm
    var dataDevolucao by Emprestimos.dataDevolucao
    var cliente_id by Emprestimos.cliente_id
    var livros by LivroDAO via EmprestimosToLivros
    var situacaoemprestimo_id by Emprestimos.situacaoemprestimo_id
}
