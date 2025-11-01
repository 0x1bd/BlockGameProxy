package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.codec.PacketCodec
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.kvxd.blockgameproxy.core.server.tick.TickSystem
import org.kvxd.blockgameproxy.core.server.tick.tasks.KeepAliveTask
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

object ProxyServer {

    private val protocol = createMinecraftProtocol()

    val codec: PacketCodec
        get() = protocol.codec

    private val networkServer: NetworkServer by lazy {
        NetworkServer(InetSocketAddress(config.bindPort)) { createMinecraftProtocol() }
    }

    var currentSession: Session? = null

    val acceptsConnections: Boolean
        get() = currentSession == null

    val LOGGER = LoggerFactory.getLogger(ProxyServer::class.java)

    fun start() {
        networkServer.addListener(ProxyServerListener())

        networkServer.bind(false)

        TickSystem.start()

        TickSystem.registerTask(KeepAliveTask())
    }

    fun stop() {
        TickSystem.stop()

        runCatching {
            networkServer.close()
        }.onFailure { t ->
            LOGGER.warn("Error while closing network server", t)
        }
    }

}