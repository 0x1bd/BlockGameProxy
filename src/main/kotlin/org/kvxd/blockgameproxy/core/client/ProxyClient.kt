package org.kvxd.blockgameproxy.core.client

import org.geysermc.mcprotocollib.network.factory.ClientNetworkSessionFactory
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.network.session.ClientNetworkSession
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.slf4j.LoggerFactory

object ProxyClient {

    private var networkClient: ClientNetworkSession? = null
    val protocol = createMinecraftProtocol()

    val LOGGER = LoggerFactory.getLogger(ProxyClient::class.java)

    fun connect(): ClientNetworkSession? {
        networkClient = ClientNetworkSessionFactory.factory()
            .setAddress(config.targetServer.host, config.targetServer.port)
            .setProtocol(protocol)
            .create()

        networkClient?.apply {
            addListener(ProxyClientReconnectListener())
            addListener(ProxyClientListener())
            connect()
        }

        return networkClient
    }

    fun send(packet: Packet) {
        networkClient?.send(packet) ?: LOGGER.warn("Cannot send packet, client not connected")
    }

}