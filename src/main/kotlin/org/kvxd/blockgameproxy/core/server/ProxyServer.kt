package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.codec.PacketCodec
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress
import java.security.KeyPair
import java.security.KeyPairGenerator

object ProxyServer {

    private val protocol = createMinecraftProtocol()

    fun getCodec(): PacketCodec = protocol.codec

    private val networkServer = NetworkServer(InetSocketAddress(config.bindPort), ::protocol)

    var currentSession: Session? = null

    val LOGGER = LoggerFactory.getLogger("Server")

    fun start() {
        networkServer.addListener(ProxyServerListener())

        networkServer.bind(false)
    }

    fun stop() {
        networkServer.close()
    }

}