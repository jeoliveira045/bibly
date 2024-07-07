package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToReservas
import bibly.sys.tables.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateStatement

class ReservaRepository {
    suspend fun findAll(): List<Reserva> = DatabaseConnection.dbQuery {
        ReservaDAO.all().map(::daoToReservas)

    }

    suspend fun findById(id: Int): Reserva = DatabaseConnection.dbQuery {
        ReservaDAO.find({Reservas.id eq id})
            .limit(1)
            .map(::daoToReservas)
            .single()
    }

    suspend fun insert(reserva: Reserva) = DatabaseConnection.dbQuery{
        {
            ReservaDAO.new {
                dataReserva = reserva.dataReserva
                dataEmprestimoEm = reserva.dataEmprestimoEm
                prazoDevolucao = reserva.prazoDevolucao
                cliente_id = reserva.cliente_id
                livro_id = reserva.livro_id
                situacaoreserva_id = reserva.situacaoreserva_id
            }
        }
    }

    suspend fun update(id: Int,reserva: Reserva) = DatabaseConnection.dbQuery{

        ReservaDAO.findByIdAndUpdate(id) {

            it.dataReserva = reserva.dataReserva
            it.dataEmprestimoEm = reserva.dataEmprestimoEm
            it.prazoDevolucao = reserva.prazoDevolucao
            it.cliente_id = reserva.cliente_id
            it.livro_id = reserva.livro_id
            it.situacaoreserva_id = reserva.situacaoreserva_id
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery {
        Reservas.deleteWhere { Reservas.id eq  id }
    }
}



