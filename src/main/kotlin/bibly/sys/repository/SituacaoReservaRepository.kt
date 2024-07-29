package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToSituacaoReservas
import bibly.sys.tables.SituacaoReserva
import bibly.sys.tables.SituacaoReservaDAO
import bibly.sys.tables.SituacaoReservas
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere

class SituacaoReservaRepository {
    suspend fun findAll(): List<SituacaoReserva> = DatabaseConnection.dbQuery {
        SituacaoReservaDAO.all().map(::daoToSituacaoReservas)
    }

    suspend fun findById(id: Int): SituacaoReserva = DatabaseConnection.dbQuery {
        SituacaoReservaDAO.find({ SituacaoReservas.id eq id }).limit(1).map(::daoToSituacaoReservas).single()
    }

    suspend fun insert(emprestimo: SituacaoReserva) = DatabaseConnection.dbQuery{
        SituacaoReservaDAO.new {
            descricao = emprestimo.descricao!!
//            situacaoemprestimo_id = emprestimo.situacaoemprestimo_id
        }
    }

    suspend fun update(id: Int,emprestimo: SituacaoReserva) = DatabaseConnection.dbQuery{
        SituacaoReservaDAO.findByIdAndUpdate(id) {
            it.descricao = emprestimo.descricao!!
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        SituacaoReservas.deleteWhere { SituacaoReservas.id eq  id }
    }
}
