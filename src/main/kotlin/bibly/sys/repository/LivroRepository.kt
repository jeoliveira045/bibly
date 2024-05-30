package bibly.sys.repository

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.Livros
import bibly.sys.plugins.DatabaseConnection.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class LivroRepository {

    suspend fun findAll(): List<Livro> = dbQuery {
        Livros.selectAll().map { row ->
            Livro(id = row[Livros.id], nome = row[Livros.nome], isbn = row[Livros.isbn], autor = row[Livros.autor], genero = row[Livros.genero])
        }
    }

    suspend fun findById(id: Int): Livro = dbQuery {
        Livros.select{Livros.id eq id}.map { row ->
            Livro(id = row[Livros.id], nome = row[Livros.nome], isbn = row[Livros.isbn], autor = row[Livros.autor], genero = row[Livros.genero])
        }.single()
    }

    suspend fun insert(livro: Livro): Unit{
        Livros.insert {
            it[Livros.id] = livro.id
            it[Livros.nome] = livro.nome
            it[Livros.isbn] = livro.isbn
            it[Livros.autor] = livro.autor
            it[Livros.genero] = livro.genero
        }
    }

    suspend fun update(id: Int,livro: Livro): Unit{
        Livros.update({Livros.id eq  id}) {
            it[Livros.id] = livro.id
            it[Livros.nome] = livro.nome
            it[Livros.isbn] = livro.isbn
            it[Livros.autor] = livro.autor
            it[Livros.genero] = livro.genero
        }
    }

    suspend fun delete(id: Int){
        Livros.deleteWhere { Livros.id eq  id }
    }
}
