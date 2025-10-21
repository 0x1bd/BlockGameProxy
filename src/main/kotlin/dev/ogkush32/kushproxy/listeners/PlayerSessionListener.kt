package dev.ogkush32.kushproxy.listeners

import dev.ogkush32.kushproxy.ProxyClient
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket

class PlayerSessionListener(private val client: ProxyClient) : SessionAdapter() {

    override fun packetReceived(session: Session, packet: Packet) {
        println("Player → Proxy → Server: ${packet.javaClass.simpleName}")

        if (packet is ClientIntentionPacket) {
            handleIntentionPacket(session, packet)
        }

        client.send(packet)
    }

    private fun handleIntentionPacket(session: Session, packet: ClientIntentionPacket) {
        val intent = packet.intent
        val newState = when (intent) {
            HandshakeIntent.LOGIN, HandshakeIntent.TRANSFER -> ProtocolState.LOGIN
            HandshakeIntent.STATUS -> ProtocolState.STATUS
        }

        session.switchInboundState { session.packetProtocol.inboundState = newState }
        session.switchOutboundState { session.packetProtocol.outboundState = newState }
        println("ProxyServer switched player session to $newState")
    }

    override fun disconnected(event: DisconnectedEvent) {
        println("Player disconnected, closing ProxyClient")
        client.disconnect("Player disconnected")
    }
}