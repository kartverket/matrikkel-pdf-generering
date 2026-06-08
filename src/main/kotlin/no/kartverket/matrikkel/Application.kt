package no.kartverket.matrikkel

import io.ktor.server.netty.*

fun runApplication() {
//    val config = Configuration()
//    DataSourceConfiguration.migrate(config.database)

    KtorServer.create(factory = Netty, port = 8086){
        //konfigurer her
        configureRouting()

    }.start(wait = true)
}