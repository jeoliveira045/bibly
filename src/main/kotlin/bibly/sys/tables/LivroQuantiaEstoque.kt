package bibly.sys.tables

import bibly.sys.plugins.tables.Livro
import bibly.sys.plugins.tables.Livros
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.View
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class LivroQuantiaEstoque(
    var id: Int?,
    var quantia: Int,
    var livro_id: Int
)

object LivrosQuantiaEstoque: IntIdTable(){
    var quantia = integer("quantia")
    var livro_id = integer("livro_id").references(Livros.id)
}

class LivrosQuantiaEstoqueDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<LivrosQuantiaEstoqueDAO>(Livros)
    var quantia by LivrosQuantiaEstoque.quantia
    var livro_id by LivrosQuantiaEstoque.livro_id
}




