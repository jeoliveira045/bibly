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
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

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
//            situacaoemprestimo_id = emprestimo.situacaoemprestimo_id
        }
    }

    suspend fun update(id: Int,emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        EmprestimoDAO.findByIdAndUpdate(id) {
            updateMapping(it, emprestimo)
        }
    }

    suspend fun delete(id: Int) = DatabaseConnection.dbQuery{
        Emprestimos.deleteWhere { Emprestimos.id eq  id }
    }
}

fun listToSizedIterable(list: List<Livro>): SizedIterable<LivroDAO> {
    var idList : List<Int> = list.map { livro -> livro.id!! }
    return LivroDAO.find { Livros.id inList(idList) }
}

fun updateMapping(it: EmprestimoDAO, emprestimo: Emprestimo){
    for(daofield in it::class.declaredMemberProperties){
        var objField = emprestimo::class.declaredMemberProperties.find { it.name == daofield.name }
        if(objField != null){
            daofield.isAccessible = true
            if(daofield.returnType.toString().contains("SizedIterable")){
                (daofield as KMutableProperty<EmprestimoDAO>).setter.call(it, listToSizedIterable(objField.getter.call(emprestimo) as List<Livro>))
            }else{
                (daofield as KMutableProperty<EmprestimoDAO>).setter.call(it, objField.getter.call(emprestimo))
            }
        }
    }
}
