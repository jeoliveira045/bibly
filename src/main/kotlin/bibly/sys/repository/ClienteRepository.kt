package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToClientes
import bibly.sys.tables.Cliente
import bibly.sys.tables.ClienteDAO
import bibly.sys.tables.Clientes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateStatement

class ClienteRepository {
    suspend fun findAll(): List<Cliente> = DatabaseConnection.dbQuery {
        ClienteDAO.all().map(::daoToClientes)

    }

    suspend fun findById(id: Int): Cliente = DatabaseConnection.dbQuery {
        ClienteDAO.find({Clientes.id eq id})
            .limit(1)
            .map(::daoToClientes)
            .single()
    }

    suspend fun insert(cliente: Cliente) = DatabaseConnection.dbQuery{
         {
            ClienteDAO.new {
                nome = cliente.nome
                sobrenome = cliente.sobrenome
                genero = cliente.genero
                cpf = cliente.cpf
                endereco = cliente.endereco
                datanascimento = cliente.datanascimento
            }
        }
    }

    suspend fun update(id: Int,cliente: Cliente) = DatabaseConnection.dbQuery{
        Clientes.update {
            it[Clientes.nome] = cliente.nome
            it[Clientes.sobrenome] = cliente.sobrenome
            it[Clientes.genero] = cliente.genero
            it[Clientes.cpf] = cliente.cpf
            it[Clientes.endereco] = cliente.endereco
            it[Clientes.datanascimento] = cliente.datanascimento
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery {
        Clientes.deleteWhere { Clientes.id eq  id }
    }
}

