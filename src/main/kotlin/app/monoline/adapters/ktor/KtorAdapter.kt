package app.monoline.adapters.ktor

import app.monoline.adapters.ktor.plugins.configureRouting
import configureContentNegotiation
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun initKtor() {
    embeddedServer(Netty, port = 8080) {
        configureContentNegotiation()
        configureRouting()
    }.start(true)
}
