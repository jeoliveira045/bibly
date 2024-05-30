package bibly.sys.repository

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.tables.Emprestimo
import bibly.sys.tables.Emprestimos
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class EmprestimoRepository {
    suspend fun findAll(): List<Emprestimo> = DatabaseConnection.dbQuery {
        Emprestimos.selectAll().map { row ->
            Emprestimo(
                id = row[Emprestimos.id],
                dtEmprestimoEm = row[Emprestimos.dtEmprestimoEm],
                prazoDevolucaoEm = row[Emprestimos.prazoDevolucaoEm],
                dataDevolucao = row[Emprestimos.dataDevolucao],
                solicitante_id = row[Emprestimos.solicitante_id],
                livro_id = row[Emprestimos.livro_id],
            )
        }
    }

    suspend fun findById(id: Int): Emprestimo = DatabaseConnection.dbQuery {
        Emprestimos.select { Emprestimos.id eq id }.map { row ->
            Emprestimo(
                id = row[Emprestimos.id],
                dtEmprestimoEm = row[Emprestimos.dtEmprestimoEm],
                prazoDevolucaoEm = row[Emprestimos.prazoDevolucaoEm],
                dataDevolucao = row[Emprestimos.dataDevolucao],
                solicitante_id = row[Emprestimos.solicitante_id],
                livro_id = row[Emprestimos.livro_id],
            )
        }.single()
    }

    suspend fun insert(livro: Emprestimo): Unit{
        Emprestimos.insert {
            it[Emprestimos.id] = livro.id
            it[Emprestimos.dtEmprestimoEm] = livro.dtEmprestimoEm
            it[Emprestimos.prazoDevolucaoEm] = livro.prazoDevolucaoEm
            it[Emprestimos.dataDevolucao] = livro.dataDevolucao
            it[Emprestimos.solicitante_id] = livro.solicitante_id
            it[Emprestimos.livro_id] = livro.livro_id

        }
    }

    suspend fun update(id: Int,livro: Emprestimo): Unit{
        Emprestimos.update({ Emprestimos.id eq  id}) {
            it[Emprestimos.id] = livro.id
            it[Emprestimos.dtEmprestimoEm] = livro.dtEmprestimoEm
            it[Emprestimos.prazoDevolucaoEm] = livro.prazoDevolucaoEm
            it[Emprestimos.dataDevolucao] = livro.dataDevolucao
            it[Emprestimos.solicitante_id] = livro.solicitante_id
            it[Emprestimos.livro_id] = livro.livro_id
        }
    }

    suspend fun delete(id: Int){
        Emprestimos.deleteWhere { Emprestimos.id eq  id }
    }
}
