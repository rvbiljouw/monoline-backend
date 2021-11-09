import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*

fun Application.configureContentNegotiation() {
    install(ContentNegotiation) {
        jackson()
    }
}
