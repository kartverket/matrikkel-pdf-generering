package no.kartverket.matrikkel

import io.ktor.server.application.*
import io.ktor.server.engine.*
import kotlin.time.Duration.Companion.seconds


object KtorServer {

    fun <TEngine : ApplicationEngine, TConfiguration : ApplicationEngine.Configuration> create(
        factory: ApplicationEngineFactory<TEngine, TConfiguration>,
        port: Int = 8080,
        configure: TConfiguration.() -> Unit = {},
        application: Application.() -> Unit,
    ): EmbeddedServer<TEngine, TConfiguration> {
        return embeddedServer(
            factory,
            serverConfig(applicationEnvironment()) {
                module {
                    application()
                }
            },
            configure = {
                connectors.add(
                    EngineConnectorBuilder().apply {
                        this.port = port
                    },
                )
                shutdownGracePeriod = 5.seconds.inWholeMilliseconds
                shutdownTimeout = 30.seconds.inWholeMilliseconds
                configure()
            },
        )
    }
}