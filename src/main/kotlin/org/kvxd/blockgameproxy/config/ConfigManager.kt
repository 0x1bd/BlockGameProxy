package org.kvxd.blockgameproxy.config

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import java.io.File

object ConfigManager {

    val CONFIG_FILE = File("./config.yml")

    var config: Config = Config()
        private set

    var firstRun: Boolean = false
        private set

    fun loadConfig() {
        val existedBefore = CONFIG_FILE.exists()
        firstRun = !existedBefore

        if (existedBefore) {
            val text = CONFIG_FILE.readText()
            config = Yaml.default.decodeFromString(text)
        } else {
            saveConfig()
        }
    }

    fun saveConfig() {
        val text = Yaml.default.encodeToString(config)
        CONFIG_FILE.writeText(text)
    }
}
