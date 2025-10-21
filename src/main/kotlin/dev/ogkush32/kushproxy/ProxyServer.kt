package dev.ogkush32.kushproxy

import dev.ogkush32.kushproxy.listeners.ServerEventListener
import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import java.net.InetSocketAddress

class ProxyServer(
    private val bindHost: String,
    private val bindPort: Int,
    private val remoteHost: String,
    private val remotePort: Int
) {
    private val networkServer = NetworkServer(
        InetSocketAddress(bindHost, bindPort)
    ) { MinecraftProtocol().apply { isUseDefaultListeners = false } }

    fun start() {
        println("Proxy listening on $bindHost:$bindPort → forwarding to $remoteHost:$remotePort")

        networkServer.addListener(ServerEventListener(remoteHost, remotePort))
        networkServer.bind(true)
    }
}