package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToReservas
import bibly.sys.tables.Reserva
import bibly.sys.tables.ReservaDAO
import bibly.sys.tables.Reservas
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

    suspend fun insert(cliente: Reserva) = DatabaseConnection.dbQuery{
        {
            ReservaDAO.new {
                dataReserva = cliente.dataReserva
                dataEmprestimoEm = cliente.dataEmprestimoEm
                prazoDevolucao = cliente.prazoDevolucao
                cliente_id = cliente.cliente_id
                livro_id = cliente.livro_id
            }
        }
    }

    suspend fun update(id: Int,cliente: Reserva) = DatabaseConnection.dbQuery{
        Reservas.update {
            it[Reservas.dataReserva] = cliente.dataReserva
            it[Reservas.dataEmprestimoEm] = cliente.dataEmprestimoEm
            it[Reservas.prazoDevolucao]  = cliente.prazoDevolucao
            it[Reservas.cliente_id] = cliente.cliente_id
            it[Reservas.livro_id] = cliente.livro_id
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery {
        Reservas.deleteWhere { Reservas.id eq  id }
    }
}

