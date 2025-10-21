package dev.ogkush32.kushproxy

import dev.ogkush32.kushproxy.listeners.ClientSessionListener
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.factory.ClientNetworkSessionFactory
import org.geysermc.mcprotocollib.network.session.ClientNetworkSession
import org.geysermc.mcprotocollib.protocol.MinecraftProtocol
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import java.net.InetSocketAddress

class ProxyClient(private val remoteHost: String, private val remotePort: Int) {

    private lateinit var networkClient: ClientNetworkSession
    private var currentSession: Session? = null
    private var nextState = ProtocolState.HANDSHAKE

    var onPacketReceived: ((Packet) -> Unit)? = null

    fun start() {
        val protocol = MinecraftProtocol().apply { isUseDefaultListeners = false }

        networkClient = ClientNetworkSessionFactory.factory()
            .setProtocol(protocol)
            .setRemoteSocketAddress(InetSocketAddress(remoteHost, remotePort))
            .create()

        networkClient.addListener(ClientSessionListener(this))
        networkClient.connect()
    }

    fun send(packet: Packet) {
        if (currentSession == null) {
            println("ProxyClient not connected yet, skipping ${packet.javaClass.simpleName}")
            return
        }

        if (packet is ClientIntentionPacket) {
            handleIntentionPacket(currentSession!!, packet)
            return
        }

        currentSession!!.switchState(nextState)

        println("Sending ${packet.javaClass.simpleName} in state ${currentSession!!.packetProtocol.inboundState} & ${currentSession!!.packetProtocol.outboundState}")

        currentSession!!.send(packet)
    }

    private fun handleIntentionPacket(session: Session, packet: ClientIntentionPacket) {
        val newState = when (packet.intent) {
            HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
            HandshakeIntent.STATUS -> ProtocolState.STATUS
        }

        session.send(packet)

        session.switchState(newState)

        nextState = newState
        println("ProxyClient switched to $newState state due to ClientIntentionPacket")
    }

    fun disconnect(reason: String) {
        if (::networkClient.isInitialized) {
            networkClient.disconnect(reason)
        }
    }

    internal fun setCurrentSession(session: Session?) {
        currentSession = session
    }

    internal fun setNextState(state: ProtocolState) {
        nextState = state
    }
}