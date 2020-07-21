package config

object AppConfig {
    const val id = "app.boletinhos"
    const val testRunner = "app.boletinhos.runner.TestRunner"

    object Sdk {
        const val min = 16
        const val target = 29
        const val compile = 29
    }

    object Version {
        const val code = 1
        override fun toString() = "0.0.1a"
    }
}