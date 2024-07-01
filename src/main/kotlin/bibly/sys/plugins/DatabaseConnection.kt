package bibly.sys.plugins

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.LivroDAO
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConnection {
    fun init(){
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/biblyk"
        val database = Database.connect(jdbcUrl, driverClassName,"biblyk", "biblyk")
        transaction(database) {
            SchemaUtils.create(Livros)
            SchemaUtils.create(Solicitantes)
            SchemaUtils.create(Emprestimos)
        }

    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction (Dispatchers.IO) { block() }
}

fun daoToSolicitantes(dao: SolicitanteDAO) = Solicitante(
    id = dao.id.value,
    nome = dao.nome,
    sobrenome = dao.sobrenome,
    endereco = dao.endereco,
    cpf = dao.cpf,
    genero = dao.genero,
    datanascimento = dao.datanascimento
)

fun daoToLivros(dao: LivroDAO) = Livro(
    id = dao.id.value,
    nome = dao.nome,
    isbn = dao.isbn,
    autor = dao.autor,
    genero = dao.genero
)

fun daoToEmprestimos(dao: EmprestimoDAO) = Emprestimo(
    id = dao.id.value,
    dtEmprestimoEm = dao.dtEmprestimoEm,
    prazoDevolucaoEm = dao.prazoDevolucaoEm,
    dataDevolucao = dao.dataDevolucao,
    solicitante_id = dao.solicitante_id,
    livro_id = dao.livro_id
)
