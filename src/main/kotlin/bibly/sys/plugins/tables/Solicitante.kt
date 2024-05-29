package bibly.sys.plugins.tables

import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDate
import org.h2.table.Table

@Serializable
data class Solicitante(
    var id: Long,
    var nome: String,
    var sobrenome: String,
    var dataNascimento: LocalDate,
    var genero: String,
    var endereco: String,
    var CPF: String,
)

object Solicitantes: Table(){

}
