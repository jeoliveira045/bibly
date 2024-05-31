package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.Livros
import bibly.sys.plugins.DatabaseConnection.dbQuery
import bibly.sys.plugins.daoToLivros
import bibly.sys.plugins.tables.LivroDAO
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class LivroRepository {

    suspend fun findAll(): List<Livro> = dbQuery {
        LivroDAO.all().map(::daoToLivros)
    }


    suspend fun findById(id: Int): Livro = dbQuery {
        LivroDAO.find({Livros.id eq id})
            .limit(1)
            .map(::daoToLivros)
            .single()
    }

    suspend fun insert(livro: Livro) = DatabaseConnection.dbQuery{
        LivroDAO.new {
            nome =  livro.nome
            isbn = livro.isbn
            genero = livro.genero
            autor = livro.autor
        }
    }

    suspend fun update(id: Int,livro: Livro) = DatabaseConnection.dbQuery{
        Livros.update({Livros.id eq  id}) {
            it[Livros.id] = livro.id!!
            it[Livros.nome] = livro.nome
            it[Livros.isbn] = livro.isbn
            it[Livros.autor] = livro.autor
            it[Livros.genero] = livro.genero
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        Livros.deleteWhere { Livros.id eq  id }
    }
}
