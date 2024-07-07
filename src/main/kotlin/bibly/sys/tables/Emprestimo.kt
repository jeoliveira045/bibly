package bibly.sys.tables

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.LivroDAO
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.Clientes
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
    val cliente_id: Int,
    val livros: List<Livro>
)

object Emprestimos: IntIdTable(){
    val dtEmprestimoEm = date("dtEmprestimoEm")
    val prazoDevolucaoEm = date("prazoDevolucaoEm")
    val dataDevolucao = date("dataDevolucao")
    val cliente_id = integer("cliente_id").references(Clientes.id)
    val livro_id = integer("livro_id").references(Livros.id)
}

class EmprestimoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<EmprestimoDAO>(Emprestimos)
    var dtEmprestimoEm by Emprestimos.dtEmprestimoEm
    var prazoDevolucaoEm by Emprestimos.prazoDevolucaoEm
    var dataDevolucao by Emprestimos.dataDevolucao
    var cliente_id by Emprestimos.cliente_id
    var livros by LivroDAO via EmprestimosToLivros
}
