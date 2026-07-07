package no.kartverket.matrikkel.config

class Configuration(
    val frontendUrl: String = getConfig("FRONTEND_URL") ?: "http://localhost:3000",
)


private fun getConfig(name: String): String? {
    return System.getProperty(name, System.getenv(name))
}

private fun getRequiredConfig(name: String): String {
    return requireNotNull(getConfig(name)) {
        "$name must be defined in java properties or environment"
    }
}