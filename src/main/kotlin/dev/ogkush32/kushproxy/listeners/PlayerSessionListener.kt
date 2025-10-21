package dev.ogkush32.kushproxy.listeners

import dev.ogkush32.kushproxy.ProxyClient
import dev.ogkush32.kushproxy.switchState
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketErrorEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket

class PlayerSessionListener(private val client: ProxyClient) : SessionAdapter() {

    override fun packetReceived(session: Session, packet: Packet) {
        println("Player sent: ${packet.javaClass.simpleName} | State: ${session.packetProtocol.inboundState}")

        when (packet) {
            is ClientIntentionPacket -> handleIntentionPacket(session, packet)

            is ServerboundLoginAcknowledgedPacket -> {
                session.switchState(ProtocolState.CONFIGURATION)
            }
        }

        client.send(packet)
    }

    private fun handleIntentionPacket(session: Session, packet: ClientIntentionPacket) {
        val intent = packet.intent
        val newState = when (intent) {
            HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
            HandshakeIntent.STATUS -> ProtocolState.STATUS
        }

        session.switchState(newState)

        println("ProxyServer switched player session to $newState")
    }

    override fun packetError(event: PacketErrorEvent) {
        println("Error in PlayerSessionListener: ")
        event.cause.printStackTrace()
    }

    override fun disconnected(event: DisconnectedEvent) {
        println("Player disconnected, closing ProxyClient")
        client.disconnect("Player disconnected")
    }
}