package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToSituacaoEmprestimos
import bibly.sys.tables.SituacaoEmprestimo
import bibly.sys.tables.SituacaoEmprestimoDAO
import bibly.sys.tables.SituacaoEmprestimos
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class SituacaoEmprestimoRepository {
    suspend fun findAll(): List<SituacaoEmprestimo> = DatabaseConnection.dbQuery {
        SituacaoEmprestimoDAO.all().map(::daoToSituacaoEmprestimos)
    }

    suspend fun findById(id: Int): SituacaoEmprestimo = DatabaseConnection.dbQuery {
        SituacaoEmprestimoDAO.find({ SituacaoEmprestimos.id eq id }).limit(1).map(::daoToSituacaoEmprestimos).single()
    }

    suspend fun insert(emprestimo: SituacaoEmprestimo) = DatabaseConnection.dbQuery{
        SituacaoEmprestimoDAO.new {
            descricao = emprestimo.descricao!!
        }
    }

    suspend fun update(id: Int,emprestimo: SituacaoEmprestimo) = DatabaseConnection.dbQuery{
        SituacaoEmprestimoDAO.findByIdAndUpdate(id) {
            it.descricao = emprestimo.descricao!!
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        SituacaoEmprestimos.deleteWhere { SituacaoEmprestimos.id eq  id }
    }
}
