package app.monoline.adapters.exposed.repository

import app.monoline.domain.post.Post
import app.monoline.domain.post.PostRepository
import app.monoline.adapters.exposed.tables.PostEntity
import app.monoline.adapters.exposed.tables.PostsTable
import app.monoline.adapters.exposed.tables.PostsTable.id
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.mapLazy
import org.jetbrains.exposed.sql.transactions.transaction as tx

fun PostEntity.toPost(): Post {
    return Post(id = id.value, text = text)
}

class ExposedPostRepository : PostRepository {

    override fun findById(id: Long): Post? {
        return tx {
            PostEntity.findById(id)?.let { entity ->
                Post(id = entity.id.value, text = entity.text)
            }
        }
    }

    override fun findAll(): Iterable<Post> {
        return tx { PostEntity.all().map { it.toPost() } }
    }

    override fun save(entity: Post): Boolean {
        val inserted = tx { PostsTable.insert { it[text] = entity.text } }
        require(inserted.insertedCount >= 1) { "Database insert failed" }
        entity.id = inserted[id].value
        return entity.id > 0
    }

}
