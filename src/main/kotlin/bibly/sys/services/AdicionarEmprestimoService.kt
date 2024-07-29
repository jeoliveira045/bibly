package bibly.sys.services

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.plugins.tables.Livros
import bibly.sys.repository.ClienteRepository
import bibly.sys.repository.EmprestimoRepository
import bibly.sys.repository.ReservaRepository
import bibly.sys.tables.*
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll

class AdicionarEmprestimoService {

    var clienteRepository = ClienteRepository()

    suspend fun exec(emprestimo: Emprestimo){
        validateLivrosDisponiveis(emprestimo)
        validateQuantidadeDeLivrosEmprestados(emprestimo)
        validateReservaMaisAntiga(emprestimo)
    }

    suspend fun validateQuantidadeDeLivrosEmprestados(emprestimo: Emprestimo){
        var maisDeDoisLivrosEmprestados = emprestimo.livros.size > 2;
        if(maisDeDoisLivrosEmprestados){
            throw Exception("Quantidade máxima de livros por esmprestimo é dois")
        }
    }

    suspend fun validateReservaMaisAntiga(emprestimo: Emprestimo) = DatabaseConnection.dbQuery{
        var emprestimoRealizadoQuandoReservaExiste = emprestimo.livros.stream().anyMatch { livro ->
            val livroId = livro.id!!
            try{
                var reservaQuery = Reservas.selectAll()
                    .where { Reservas.prazoDevolucao greaterEq emprestimo.dtEmprestimoEm }
                    .andWhere { Reservas.dataEmprestimoEm lessEq emprestimo.prazoDevolucaoEm }
                    .andWhere { Reservas.livro_id eq livroId }
                    .andWhere { Reservas.situacaoreserva_id eq 3 }
                    .minBy { Reservas.dataReserva }


                if(reservaQuery?.hasValue(Reservas.dataEmprestimoEm)!!){
                    reservaQuery[Reservas.cliente_id] != emprestimo.cliente_id && reservaQuery.hasValue(Reservas.dataEmprestimoEm)
                }else {
                    false
                }
            } catch(error: NoSuchElementException){
                false
            }


        }
        if(emprestimoRealizadoQuandoReservaExiste){
            throw Exception("O " +
                    "livro emprestado " +
                    "está reservado. Contate o ultimo cliente " +
                    "que está reservando esse livro ou " +
                    "coloque a reserva para \"Não Atendida\""
            )
        }
    }



    suspend fun validateLivrosDisponiveis(emprestimo: Emprestimo) = DatabaseConnection.dbQuery {
        emprestimo.livros.forEach {livro ->
            /**
             * Verifica a quantidade de livros na biblioteca
             */
            var quantiaLivros = LivrosQuantiaEstoque.select(LivrosQuantiaEstoque.quantia)
                .where {LivrosQuantiaEstoque.livro_id eq livro.id!!}.single()

            /**
             * Verifica a quantidade de livros emprestados
             */
            var totalLivrosEmprestados = Emprestimos.join(EmprestimosToLivros, JoinType.INNER).selectAll()
                .where {EmprestimosToLivros.livro_id eq  livro.id!!}.count()

            /**
             * Verifica a quantidade de livros para emprestimo
             */

            var verificarQuantiaDeLivrosParaEmprestimo = quantiaLivros[LivrosQuantiaEstoque.quantia] - totalLivrosEmprestados

            if((verificarQuantiaDeLivrosParaEmprestimo) <= 0){
                throw Exception("O livro está indisponivel")
            }
        }
    }
}
