package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.codec.PacketCodec
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundKeepAlivePacket
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.kvxd.blockgameproxy.core.server.tick.TickSystem
import org.kvxd.blockgameproxy.core.server.tick.TickTask
import org.kvxd.blockgameproxy.core.server.tick.tasks.KeepAliveTask
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

object ProxyServer {

    private val protocol = createMinecraftProtocol()

    fun getCodec(): PacketCodec = protocol.codec

    private val networkServer = NetworkServer(InetSocketAddress(config.bindPort), ::protocol)

    val LOGGER = LoggerFactory.getLogger("Server")

    val NETWORK_CODEC = MinecraftProtocol.loadNetworkCodec()

    fun start() {
        networkServer.addListener(ProxyServerListener())

        networkServer.bind(false)

        TickSystem.start()

        TickSystem.registerTask(KeepAliveTask())
    }

    fun stop() {
        TickSystem.stop()

        networkServer.close()
    }

}