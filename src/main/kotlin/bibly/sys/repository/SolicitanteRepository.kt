package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.tables.Solicitante
import bibly.sys.plugins.tables.Solicitantes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class SolicitanteRepository {
    suspend fun findAll(): List<Solicitante> = DatabaseConnection.dbQuery {
        Solicitantes.selectAll().map { row ->
            Solicitante(
                id = row[Solicitantes.id],
                nome = row[Solicitantes.nome],
                sobrenome = row[Solicitantes.sobrenome],
                dataNascimento = row[Solicitantes.dataNascimento],
                genero = row[Solicitantes.genero],
                endereco = row[Solicitantes.endereco],
                cpf = row[Solicitantes.cpf]
            )
        }
    }

    suspend fun findById(id: Int): Solicitante = DatabaseConnection.dbQuery {
        Solicitantes.select { Solicitantes.id eq id }.map { row ->
            Solicitante(
                id = row[Solicitantes.id],
                nome = row[Solicitantes.nome],
                sobrenome = row[Solicitantes.sobrenome],
                dataNascimento = row[Solicitantes.dataNascimento],
                genero = row[Solicitantes.genero],
                endereco = row[Solicitantes.endereco],
                cpf = row[Solicitantes.cpf]
            )
        }.single()
    }

    suspend fun insert(livro: Solicitante): Unit{
        Solicitantes.insert {
            it[Solicitantes.id] = livro.id
            it[Solicitantes.nome] = livro.nome
            it[Solicitantes.sobrenome] = livro.sobrenome
            it[Solicitantes.dataNascimento] = livro.dataNascimento
            it[Solicitantes.genero] = livro.genero
            it[Solicitantes.endereco] = livro.endereco
            it[Solicitantes.cpf] = livro.cpf

        }
    }

    suspend fun update(id: Int,livro: Solicitante): Unit{
        Solicitantes.update({ Solicitantes.id eq  id}) {
            it[Solicitantes.id] = livro.id
            it[Solicitantes.nome] = livro.nome
            it[Solicitantes.sobrenome] = livro.sobrenome
            it[Solicitantes.dataNascimento] = livro.dataNascimento
            it[Solicitantes.genero] = livro.genero
            it[Solicitantes.endereco] = livro.endereco
            it[Solicitantes.cpf] = livro.cpf
        }
    }

    suspend fun delete(id: Int){
        Solicitantes.deleteWhere { Solicitantes.id eq  id }
    }
}
