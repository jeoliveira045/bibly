package bibly.sys.tables

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.Livros
import bibly.sys.plugins.tables.Solicitantes
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.date

data class Emprestimo(
    var id: Long,
    val dtEmprestimoEm: LocalDate,
    val prazoDevolucaoEm: LocalDate,
    val dataDevolucao: LocalDate,
    val solicitante_id: Int,
    val livro_id: Int
)

object Emprestimos: Table(){
    val id = integer("id").autoIncrement()
    val dtEmprestimoEm = date("dtEmprestimoEm")
    val prazoDevolucaoEm = date("prazoDevolucaoEm")
    val dataDevolucaoEm = date("dataDevolucao")
    val solicitante_id = integer("solicitante_id").references(Solicitantes.id)
    val livro_id = integer("livro_id").references(Livros.id)

    override val primaryKey = PrimaryKey(Emprestimos.id)
}
