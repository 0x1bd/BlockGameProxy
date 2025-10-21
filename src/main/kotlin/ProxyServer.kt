import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.server.*
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.network.server.NetworkServer
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
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

        networkServer.addListener(object : ServerAdapter() {
            override fun serverBound(event: ServerBoundEvent) {
                println("Proxy server bound and accepting connections.")
            }

            override fun sessionAdded(event: SessionAddedEvent) {
                val playerSession = event.session
                println("Incoming player session from ${playerSession.remoteAddress}")

                val client = ProxyClient(remoteHost, remotePort)
                client.start()

                client.onPacketReceived = { packet ->
                    println("Server → Proxy → Player: ${packet.javaClass.simpleName}")
                    playerSession.send(packet)
                }

                playerSession.addListener(object : SessionAdapter() {
                    override fun packetReceived(session: Session, packet: Packet) {
                        println("Player → Proxy → Server: ${packet.javaClass.simpleName}")

                        if (packet is ClientIntentionPacket) {
                            val intent = packet.intent
                            val newState = when (intent) {
                                HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
                                HandshakeIntent.STATUS -> ProtocolState.STATUS
                            }

                            session.switchInboundState { session.packetProtocol.inboundState = newState }
                            session.switchOutboundState { session.packetProtocol.outboundState = newState }
                            println("ProxyServer switched player session to $newState")
                        }

                        client.send(packet)
                    }

                    override fun disconnected(event: DisconnectedEvent) {
                        println("Player disconnected, closing ProxyClient")
                        client.disconnect("Player disconnected")
                    }
                })
            }

            override fun sessionRemoved(event: SessionRemovedEvent) {
                println("Player session removed: ${event.session.remoteAddress}")
            }
        })

        networkServer.bind(true)
    }
}
