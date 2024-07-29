package bibly.sys.services

import bibly.sys.plugins.DatabaseConnection
import bibly.sys.repository.ClienteRepository
import bibly.sys.tables.Cliente
import bibly.sys.tables.Clientes
import kotlinx.datetime.*
import org.jetbrains.exposed.sql.exists
import org.jetbrains.exposed.sql.selectAll


class CadastrarClienteService {
    var clienteRepository = ClienteRepository()

    suspend fun exec(resource: Cliente){
        validateClienteNaoExistente(resource)
        validateClienteMaiorDeIdade(resource)
    }

    suspend fun validateClienteNaoExistente(resource: Cliente) = DatabaseConnection.dbQuery{
        var verificarClienteExistente = Clientes.selectAll().where{Clientes.id eq resource.id!!}
        var clienteExistente = !verificarClienteExistente.empty()


        if(clienteExistente){
            throw Exception("O cliente já existe na base de dados")
        }
    }

    suspend fun validateClienteMaiorDeIdade(resource: Cliente){
        var dataDeNascimento = resource.datanascimento
        var dataAtual = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        var periodo = dataDeNascimento.periodUntil(dataAtual).years

        if(periodo < 12){
            throw Exception("O cliente precisa ter a idade mínima de 12 anos para se cadastrar. Solicite-o para cadastrar um de seus responsáveis")
        }
    }
}
