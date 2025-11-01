package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.getState
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries

class ServerSessionListener : SessionAdapter() {

    companion object {

        var currentSession: Session? = null
    }

    override fun packetReceived(session: Session, packet: Packet) {
        ProxyClient.LOGGER.debug("Packet received: {}", packet)

        val handler = PacketHandlerRegistries.SERVER
            .getIncoming(packet::class)

        val shouldForward = handler?.shouldForward == true || handler == null

        val processedPacket = handler?.process(session, packet) ?: packet

        if (shouldForward && session.getState() == ProtocolState.GAME) {
            ProxyClient.send(processedPacket)
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        ProxyClient.LOGGER.debug("Packet sent: {}", packet)

        PacketHandlerRegistries.SERVER
            .getPostOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetSending(event: PacketSendingEvent) {
        val packet = event.packet
        val session = event.session

        PacketHandlerRegistries.SERVER
            .getOutgoing(packet::class)?.process(session, packet)
    }

    override fun disconnected(event: DisconnectedEvent) {
        currentSession = null
    }

}