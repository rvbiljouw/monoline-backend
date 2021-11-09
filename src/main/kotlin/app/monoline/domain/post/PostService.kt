package app.monoline.domain.post

import app.monoline.adapters.Repository
import app.monoline.util.logger
import org.jetbrains.exposed.sql.transactions.transaction

data class Post(
    var id: Long = -1,
    var text: String,
)

interface PostRepository : Repository<Post, Long>

class PostService(private val posts: PostRepository) {
    private val log by logger()

    fun persistPost(post: Post): Boolean {
        return posts.save(post)
    }

    fun getAllPosts(): Iterable<Post> {
        return posts.findAll()
    }

}
