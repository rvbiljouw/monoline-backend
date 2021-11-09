package app.monoline.adapters.ktor

import app.monoline.domain.post.Post
import app.monoline.domain.post.PostService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.java.KoinJavaComponent.inject

internal data class CreatePostRequest(val text: String)

fun Routing.posts() {
    val postService by inject<PostService>(PostService::class.java)

    get("/api/posts") {
        call.respond(postService.getAllPosts())
    }

    post("/api/posts") {
        val request = call.receive<CreatePostRequest>()
        val post = Post(text = request.text)
        if (postService.persistPost(post)) {
            call.respond(post)
        } else {
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Failed to persist post.")
            )
        }
    }

}
