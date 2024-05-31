package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.daoToEmprestimos
import bibly.sys.tables.Emprestimo
import bibly.sys.tables.EmprestimoDAO
import bibly.sys.tables.Emprestimos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class EmprestimoRepository {
    suspend fun findAll(): List<Emprestimo> = DatabaseConnection.dbQuery {
        EmprestimoDAO.all().map(::daoToEmprestimos)
    }

    suspend fun findById(id: Int): Emprestimo = DatabaseConnection.dbQuery {
        EmprestimoDAO.find({ Emprestimos.id eq id }).limit(1).map(::daoToEmprestimos).single()
    }

    suspend fun insert(emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        Emprestimos.insert {
            it[Emprestimos.dtEmprestimoEm] = emprestimo.dtEmprestimoEm
            it[Emprestimos.prazoDevolucaoEm] = emprestimo.prazoDevolucaoEm
            it[Emprestimos.dataDevolucao] = emprestimo.dataDevolucao
            it[Emprestimos.solicitante_id] = emprestimo.solicitante_id
            it[Emprestimos.livro_id] = emprestimo.livro_id

        }
    }

    suspend fun update(id: Int,emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        Emprestimos.update({ Emprestimos.id eq  id}) {
            it[Emprestimos.id] = emprestimo.id!!
            it[Emprestimos.dtEmprestimoEm] = emprestimo.dtEmprestimoEm
            it[Emprestimos.prazoDevolucaoEm] = emprestimo.prazoDevolucaoEm
            it[Emprestimos.dataDevolucao] = emprestimo.dataDevolucao
            it[Emprestimos.solicitante_id] = emprestimo.solicitante_id
            it[Emprestimos.livro_id] = emprestimo.livro_id
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        Emprestimos.deleteWhere { Emprestimos.id eq  id }
    }
}
