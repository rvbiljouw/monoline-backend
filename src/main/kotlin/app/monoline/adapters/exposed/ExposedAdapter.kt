package app.monoline.adapters.exposed

import app.monoline.adapters.exposed.tables.PostsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun initExposedAdapter() {
    Database.connect(
        "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
        driver = "org.h2.Driver",
        user = "root",
        password = ""
    )

    transaction {
        SchemaUtils.create(PostsTable)
    }
}
