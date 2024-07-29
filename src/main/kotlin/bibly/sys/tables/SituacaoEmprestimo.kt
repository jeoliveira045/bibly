package bibly.sys.tables

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class SituacaoEmprestimo(
    val id: Int?,
    val descricao: String?
)

object SituacaoEmprestimos: IntIdTable(){
    var descricao = varchar("descricao", 255)
}

class SituacaoEmprestimoDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<SituacaoEmprestimoDAO>(SituacaoEmprestimos)
    var descricao by SituacaoEmprestimos.descricao
}
