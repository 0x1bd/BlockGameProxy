package org.kvxd.blockgameproxy

import org.kvxd.blockgameproxy.config.ConfigManager
import org.slf4j.LoggerFactory

object Bootstrap {

    private val LOGGER = LoggerFactory.getLogger(Bootstrap::class.java)

    fun main(args: Array<String>) {
        LOGGER.info("Loading configuration")

        ConfigManager.loadConfig()
        LOGGER.debug("Configuration loaded: {}", ConfigManager.config)

        if (ConfigManager.firstRun) {
            BlockGameProxy.LOGGER.info("First run detected; Cancelling initialization")
            BlockGameProxy.LOGGER.info("Config file was created")
            return
        } else {
            BlockGameProxy.initialize()
        }

        Runtime.getRuntime().addShutdownHook(Thread {
            LOGGER.info("Shutdown initiated")

            try {
                ConfigManager.saveConfig()
            } catch (ex: Exception) {
                LOGGER.error("Error during shutdown", ex)
            }
        })
    }

}