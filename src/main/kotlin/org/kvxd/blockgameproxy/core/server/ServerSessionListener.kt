package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketErrorEvent
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.getState
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries

class ServerSessionListener : SessionAdapter() {

    override fun packetReceived(session: Session, packet: Packet) {
        ProxyServer.LOGGER.debug("Packet received: {} from: {}", packet, session.remoteAddress)

        val handler = PacketHandlerRegistries.SERVER
            .getIncoming(packet::class)

        val shouldForward = handler?.shouldForward == true || handler == null

        val processedPacket = handler?.process(session, packet) ?: packet

        if (shouldForward && session.getState() == ProtocolState.GAME && session == ProxyServer.currentSession) {
            ProxyClient.send(processedPacket)
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        ProxyServer.LOGGER.debug("Packet sent: {}", packet)

        PacketHandlerRegistries.SERVER
            .getPostOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetSending(event: PacketSendingEvent) {
        ProxyServer.LOGGER.debug("Packet sending: {}", event.packet)

        val packet = event.packet
        val session = event.session

        PacketHandlerRegistries.SERVER
            .getOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetError(event: PacketErrorEvent) {
        ProxyServer.LOGGER.error("Packet Error: ${event.cause.message}")
        event.cause.printStackTrace()
    }

    override fun disconnected(event: DisconnectedEvent) {
        if (event.session == ProxyServer.currentSession) {
            ProxyServer.currentSession = null
        }
    }
}