package app.monoline.adapters.flatfile

import app.monoline.domain.post.Post
import app.monoline.domain.post.PostRepository
import app.monoline.util.logger
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.File
import java.io.IOException

class FlatfilePostRepository : PostRepository {
    private val log by logger()

    private val mapper = ObjectMapper().registerModule(KotlinModule.Builder().build())
    private val postsMap = mutableMapOf<Long, Post>()
    private val file = File("./posts.json")
    private var maxId: Long = 0

    init {
        if (!file.exists() || file.length() == 0L) {
            file.writeText("[{}]")
        }

        loadFromFs()
    }

    private fun loadFromFs() {
        try {
            val postList: List<Post> = mapper.readValue(file)
            postList.forEach {
                if (it.id > maxId) {
                    maxId = it.id
                }
                postsMap[it.id] = it
            }
            log.info("Loaded ${postList.size} posts from ${file.absolutePath}")
        } catch (e: IOException) {
            file.delete()
            file.writeText("[{}]")
        }
    }

    private fun persistToFs() {
        try {
            mapper.writeValue(file, postsMap.values)
            log.info("Wrote ${postsMap.size} posts to ${file.absolutePath}")
        } catch (e: IOException) {
            file.delete()
            file.writeText("[{}]")
        }
    }

    override fun findById(id: Long): Post? {
        return postsMap[id]
    }

    override fun findAll(): Iterable<Post> {
        return postsMap.values
    }

    override fun save(entity: Post): Boolean {
        entity.id = ++maxId
        postsMap[entity.id] = entity
        persistToFs()
        return true
    }

}
