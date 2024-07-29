package bibly.sys.plugins.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.kotlin.datetime.date

@Serializable
data class Livro(
    var id: Int?,
    var nome: String,
    var isbn: String,
    var genero: String,
    var autor: String,
    var largura: String,
    var altura: String,
    var idioma: String,
    var dataEdicao: LocalDate
)

object Livros: IntIdTable() {
    val nome = varchar("nome", 50)
    val isbn = varchar("isbn", 50)
    val genero = varchar("genero", 50)
    val autor = varchar("autor", 50)
    val largura = varchar("largura",50)
    val altura = varchar("altura",50)
    val idioma = varchar("idioma",50)
    val dataEdicao = date("dataEdicao")

}

class LivroDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<LivroDAO>(Livros)
    var nome by Livros.nome
    var isbn by Livros.isbn
    var autor by Livros.autor
    var genero by Livros.genero
    var largura by Livros.largura
    var altura by Livros.altura
    var idioma by Livros.idioma
    var dataEdicao by Livros.dataEdicao
}

