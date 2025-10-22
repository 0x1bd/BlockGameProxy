package org.kvxd.blockgameproxy.config

import kotlinx.serialization.json.Json
import java.io.File

object ConfigManager {

    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    val CONFIG_FILE = File("./config.json")

    var config: Config = Config()
        private set

    fun loadConfig() {
        if (CONFIG_FILE.exists()) {
            val text = CONFIG_FILE.readText()
            config = json.decodeFromString(text)
        } else {
            CONFIG_FILE.createNewFile()
            saveConfig()
        }
    }

    fun saveConfig() {
        val text = json.encodeToString(config)
        CONFIG_FILE.writeText(text)
    }
}
