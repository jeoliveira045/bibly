package bibly.sys.tables

import bibly.sys.plugins.tables.Livros
import org.jetbrains.exposed.sql.Table

object EmprestimosToLivros: Table(){
    val emprestimo_id = reference("emprestimo_id", Emprestimos)
    val livro_id = reference("livro_id", Livros)
    override val primaryKey = PrimaryKey(emprestimo_id, livro_id)
}





