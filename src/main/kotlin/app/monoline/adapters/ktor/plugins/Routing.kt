package app.monoline.adapters.ktor.plugins

import app.monoline.adapters.ktor.posts
import io.ktor.routing.*
import io.ktor.application.*

fun Application.configureRouting() {
    routing {
        posts()
    }
}
