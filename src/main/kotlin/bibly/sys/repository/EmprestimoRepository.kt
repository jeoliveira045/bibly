package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToEmprestimos
import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.LivroDAO
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.Emprestimo
import bibly.sys.tables.EmprestimoDAO
import bibly.sys.tables.Emprestimos
import bibly.sys.tables.EmprestimosToLivros
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList

class EmprestimoRepository {
    suspend fun findAll(): List<Emprestimo> = DatabaseConnection.dbQuery {
        EmprestimoDAO.all().map(::daoToEmprestimos)
    }

    suspend fun findById(id: Int): Emprestimo = DatabaseConnection.dbQuery {
        EmprestimoDAO.find({ Emprestimos.id eq id }).limit(1).map(::daoToEmprestimos).single()
    }

    suspend fun insert(emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        EmprestimoDAO.new {
            dtEmprestimoEm = emprestimo.dtEmprestimoEm
            prazoDevolucaoEm = emprestimo.prazoDevolucaoEm
            dataDevolucao = emprestimo.dataDevolucao
            cliente_id = emprestimo.cliente_id
            livros = listToSizedIterable(emprestimo.livros)
        }
    }

    suspend fun update(id: Int,emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        EmprestimoDAO.findByIdAndUpdate(id) {
            it.dtEmprestimoEm = emprestimo.dtEmprestimoEm
            it.prazoDevolucaoEm = emprestimo.prazoDevolucaoEm
            it.dataDevolucao = emprestimo.dataDevolucao
            it.cliente_id = emprestimo.cliente_id
            it.livros = listToSizedIterable(emprestimo.livros)
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        Emprestimos.deleteWhere { Emprestimos.id eq  id }
    }
}

fun listToSizedIterable(list: List<Livro>): SizedIterable<LivroDAO> {
    var intList : List<Int> = list.map { livro -> livro.id!! }
    return LivroDAO.find { Livros.id inList(intList) }
}
