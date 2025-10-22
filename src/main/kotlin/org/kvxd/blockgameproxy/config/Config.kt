package org.kvxd.blockgameproxy.config

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val bindPort: Int = 25566,
    val targetServerHost: String = "0.0.0.0",
    val targetServerPort: Int = 25565,

    val profileName: String = "OGKushProxy"
)

val config: Config
    get() = ConfigManager.config