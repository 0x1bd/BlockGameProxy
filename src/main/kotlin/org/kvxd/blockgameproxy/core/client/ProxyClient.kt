package org.kvxd.blockgameproxy.core.client

import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.geysermc.mcprotocollib.network.factory.ClientNetworkSessionFactory
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.network.session.ClientNetworkSession
import org.slf4j.LoggerFactory

object ProxyClient {

    private lateinit var networkClient: ClientNetworkSession
    val protocol = createMinecraftProtocol()

    val LOGGER = LoggerFactory.getLogger("ProxyClient")

    fun initialize() {
        networkClient = ClientNetworkSessionFactory.factory()
            .setAddress(config.targetServerHost, config.targetServerPort)
            .setProtocol(protocol)
            .create()

        networkClient.addListener(ProxyClientListener())
    }

    fun connect() {
        if (!::networkClient.isInitialized)
            throw IllegalStateException("ProxyClient not initialized")

        networkClient.connect()
    }

    fun send(packet: Packet) {
        networkClient.send(packet)
    }

}