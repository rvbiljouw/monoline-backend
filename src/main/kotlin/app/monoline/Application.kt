package app.monoline

import app.monoline.domain.post.PostRepository
import app.monoline.domain.post.PostService
import app.monoline.adapters.ktor.initKtor
import app.monoline.adapters.exposed.initExposedAdapter
import app.monoline.adapters.exposed.repository.ExposedPostRepository
import org.koin.core.context.startKoin
import org.koin.dsl.binds
import org.koin.dsl.module

fun main() {
    val monolineModule = module {
        single { ExposedPostRepository() } binds arrayOf(PostRepository::class)
        single { PostService(get()) }
    }

    startKoin {
        modules(monolineModule)

        initExposedAdapter()
        initKtor()
    }
}
