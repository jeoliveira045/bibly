package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToSolicitantes
import bibly.sys.tables.Solicitante
import bibly.sys.tables.SolicitanteDAO
import bibly.sys.tables.Solicitantes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class SolicitanteRepository {
    suspend fun findAll(): List<Solicitante> = DatabaseConnection.dbQuery {
        SolicitanteDAO.all().map(::daoToSolicitantes)
    }

    suspend fun findById(id: Int): Solicitante = DatabaseConnection.dbQuery {
        SolicitanteDAO.find({Solicitantes.id eq id})
            .limit(1)
            .map(::daoToSolicitantes)
            .single()
    }

    suspend fun insert(solicitante: Solicitante) = DatabaseConnection.dbQuery{
         {
            SolicitanteDAO.new {
                nome = solicitante.nome
                sobrenome = solicitante.sobrenome
                genero = solicitante.genero
                cpf = solicitante.cpf
                endereco = solicitante.endereco
                datanascimento = solicitante.datanascimento
            }
        }
    }

    suspend fun update(id: Int,solicitante: Solicitante) = DatabaseConnection.dbQuery{
        Solicitantes.update {
            it[Solicitantes.nome] = solicitante.nome
            it[Solicitantes.sobrenome] = solicitante.sobrenome
            it[Solicitantes.genero] = solicitante.genero
            it[Solicitantes.cpf] = solicitante.cpf
            it[Solicitantes.endereco] = solicitante.endereco
            it[Solicitantes.datanascimento] = solicitante.datanascimento
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery {
        Solicitantes.deleteWhere { Solicitantes.id eq  id }
    }
}
