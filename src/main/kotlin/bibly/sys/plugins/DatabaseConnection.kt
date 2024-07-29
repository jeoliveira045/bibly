package bibly.sys.plugins

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.LivroDAO
import bibly.sys.plugins.tables.Livros
import bibly.sys.tables.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.SchemaMetadata

object DatabaseConnection {
    fun init(){
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/biblyk"
        val database = Database.connect(jdbcUrl, driverClassName,"biblyk", "biblyk")
        transaction(database) {
            SchemaUtils.create(
                Livros,
                Clientes,
                Emprestimos,
                Reservas,
                EmprestimosToLivros,
                SituacaoEmprestimos,
                SituacaoReservas,
                LivrosQuantiaEstoque
            )
        }

    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction (Dispatchers.IO) { block() }
}

fun daoToClientes(dao: ClienteDAO) = Cliente(
    id = dao.id.value,
    nome = dao.nome,
    sobrenome = dao.sobrenome,
    endereco = dao.endereco,
    cpf = dao.cpf,
    genero = dao.genero,
    datanascimento = dao.datanascimento,
    responsavel = dao.responsavel,
    cpfResponsavel = dao.cpfResponsavel,
    comprovanteResidencia = dao.comprovanteResidencia,
    certidaoNascimento = dao.certidaoNascimento,
    rg = dao.rg,
    numeroTelefone = dao.numeroTelefone,
)

fun daoToLivros(dao: LivroDAO) = Livro(
    id = dao.id.value,
    nome = dao.nome,
    isbn = dao.isbn,
    autor = dao.autor,
    genero = dao.genero,
    largura = dao.largura,
    altura = dao.altura,
    idioma = dao.idioma,
    dataEdicao = dao.dataEdicao
)

fun daoToEmprestimos(dao: EmprestimoDAO) = Emprestimo(
    id = dao.id.value,
    dtEmprestimoEm = dao.dtEmprestimoEm,
    prazoDevolucaoEm = dao.prazoDevolucaoEm,
    dataDevolucao = dao.dataDevolucao,
    cliente_id = dao.cliente_id,
    livros = dao.livros.map(::daoToLivros),
    situacaoemprestimo_id = dao.situacaoemprestimo_id
)

fun daoToReservas(dao: ReservaDAO) = Reserva(
    id = dao.id.value,
    dataReserva = dao.dataReserva,
    dataEmprestimoEm = dao.dataEmprestimoEm,
    prazoDevolucao = dao.prazoDevolucao,
    cliente_id = dao.cliente_id,
    livro_id = dao.livro_id,
    situacaoreserva_id = dao.situacaoreserva_id
)

fun daoToSituacaoReservas(dao: SituacaoReservaDAO) = SituacaoReserva(
    id = dao.id.value,
    descricao = dao.descricao
)

fun daoToSituacaoEmprestimos(dao: SituacaoEmprestimoDAO) = SituacaoEmprestimo(
    id = dao.id.value,
    descricao = dao.descricao
)
