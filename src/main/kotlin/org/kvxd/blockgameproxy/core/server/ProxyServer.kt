package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.codec.PacketCodec
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundKeepAlivePacket
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.createMinecraftProtocol
import org.kvxd.blockgameproxy.core.server.tick.TickSystem
import org.kvxd.blockgameproxy.core.server.tick.TickTask
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

        TickSystem.registerTask(object : TickTask {
            override fun tick(currentTick: Int) {
                val fifteenSecondsTicks = 15 * 20

                if (currentTick % fifteenSecondsTicks == 0) {
                    println("Sending keep alive. ${ServerSessionListener.currentSession}")

                    ServerSessionListener.currentSession?.send(
                        ClientboundKeepAlivePacket(System.currentTimeMillis())
                    )
                }
            }
        })
    }

    fun stop() {
        TickSystem.stop()

        networkServer.close()
    }

}