package no.kartverket.matrikkel.config

class Configuration(
    val frontendUrl: String = getConfig("FRONTEND_URL") ?: "http://localhost:3000",
)


private fun getConfig(name: String): String? {
    return System.getProperty(name, System.getenv(name))
}
