import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.*
import org.geysermc.mcprotocollib.network.factory.ClientNetworkSessionFactory
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.network.session.ClientNetworkSession
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.compression.ZlibCompression
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import java.net.InetSocketAddress

class ProxyClient(private val remoteHost: String, private val remotePort: Int) {

    private lateinit var networkClient: ClientNetworkSession
    private var currentSession: Session? = null

    var onPacketReceived: ((Packet) -> Unit)? = null

    fun start() {
        val protocol = MinecraftProtocol().apply { isUseDefaultListeners = false }

        networkClient = ClientNetworkSessionFactory.factory()
            .setProtocol(protocol)
            .setRemoteSocketAddress(InetSocketAddress(remoteHost, remotePort))
            .create()

        networkClient.addListener(object : SessionAdapter() {
            override fun connected(event: ConnectedEvent) {
                println("Connected to target server at $remoteHost:$remotePort")
                currentSession = event.session
            }

            override fun disconnected(event: DisconnectedEvent) {
                println("Disconnected from target server at $remoteHost:$remotePort")
                currentSession = null
            }

            override fun packetReceived(session: Session, packet: Packet) {
                println("ProxyClient received from server: ${packet.javaClass.simpleName}")

                when (packet) {
                    is ClientboundLoginCompressionPacket -> {
                        onPacketReceived?.invoke(packet)

                        val threshold = packet.threshold
                        if (threshold >= 0) {
                            session.setCompression(CompressionConfig(threshold, ZlibCompression(), false))
                        }
                    }

                    is ClientboundLoginFinishedPacket -> {
                        onPacketReceived?.invoke(packet)

                        session.switchInboundState { session.packetProtocol.inboundState = ProtocolState.CONFIGURATION }
                        session.switchOutboundState { session.packetProtocol.outboundState = ProtocolState.CONFIGURATION }

                        nextState = ProtocolState.CONFIGURATION
                    }

                    else -> {
                        onPacketReceived?.invoke(packet)
                    }
                }
            }

            override fun packetSending(event: PacketSendingEvent) {
                println("ProxyClient forwarding packet to server: ${event.packet.javaClass.simpleName}")
            }

            override fun packetSent(session: Session, packet: Packet) {
                println("ProxyClient forwarded packet to server: ${packet.javaClass.simpleName}")
            }
        })

        networkClient.connect()
    }

    private var nextState = ProtocolState.HANDSHAKE

    fun send(packet: Packet) {
        if (currentSession == null) {
            println("ProxyClient not connected yet, skipping ${packet.javaClass.simpleName}")
            return
        }

        val session = currentSession!!

        if (packet is ClientIntentionPacket) {
            val newState = when (packet.intent) {
                HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
                HandshakeIntent.STATUS -> ProtocolState.STATUS
            }

            session.switchInboundState { session.packetProtocol.inboundState = ProtocolState.HANDSHAKE }
            session.switchOutboundState { session.packetProtocol.outboundState = ProtocolState.HANDSHAKE }

            session.send(packet)

            session.switchInboundState { session.packetProtocol.inboundState = newState }
            session.switchOutboundState { session.packetProtocol.outboundState = newState }

            println("ProxyClient switched to $newState state due to ClientIntentionPacket")
            nextState = newState
            return
        }

        session.packetProtocol.inboundState = nextState
        session.packetProtocol.outboundState = nextState
        session.send(packet)
    }

    fun disconnect(reason: String) {
        if (::networkClient.isInitialized) {
            networkClient.disconnect(reason)
        }
    }
}
