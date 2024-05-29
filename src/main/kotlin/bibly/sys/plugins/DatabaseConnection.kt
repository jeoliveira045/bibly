package bibly.sys.plugins

import bibly.sys.plugins.tables.Livros
import bibly.sys.plugins.tables.Solicitantes
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseConnection {
    fun init(){
        val driverClassName = "org.postgresql.Driver"
        val jdbcUrl = "jdbc:postgresql://localhost:5432/bibly"
        val database = Database.connect(jdbcUrl, driverClassName,"bibly", "bibly")
        transaction(database) {
            SchemaUtils.create(Livros)
            SchemaUtils.create(Solicitantes)
        }

    }
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction (Dispatchers.IO) { block() }
}
