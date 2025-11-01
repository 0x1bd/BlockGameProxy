package org.kvxd.blockgameproxy.config

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val bindPort: Int = 25566,
    val targetServer: TargetServer = TargetServer(),

    val profileName: String = "BlockGameProxy"
)

@Serializable
data class TargetServer(
    var host: String = "0.0.0.0",
    var port: Int = 25565
)

val config: Config
    get() = ConfigManager.config