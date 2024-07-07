package bibly.sys.tables

import bibly.sys.plugins.tables.Livros
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

data class SituacaoReserva(
    val id: Int?,
    val descricao: String?
)

object SituacaoReservas: IntIdTable(){
    var descricao = varchar("descricao", 255)
}

class SituacaoReservaDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<SituacaoReservaDAO>(SituacaoReservas)
    var descricao by SituacaoReservas.descricao
}





