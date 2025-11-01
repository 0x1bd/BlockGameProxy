package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.ConnectedEvent
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.getState
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries

class ServerSessionListener : SessionAdapter() {

    override fun connected(event: ConnectedEvent) {
        ProxyServer.LOGGER.info("Server Session established: ${event.session.remoteAddress}")

        currentSession = event.session
    }

    companion object {
        var currentSession: Session? = null
    }

    override fun packetReceived(session: Session, packet: Packet) {
        ProxyServer.LOGGER.info("rec: $packet")

        val handler = PacketHandlerRegistries.SERVER
            .getIncoming(packet::class)

        val shouldForward = handler?.shouldForward == true || handler == null

        val processedPacket = handler?.process(session, packet) ?: packet

        if (shouldForward && session.getState() == ProtocolState.GAME) {
            println("FORWARDING PACKET TO TARGET SERVER: ${processedPacket.javaClass.simpleName}")

            ProxyClient.send(processedPacket)
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        ProxyServer.LOGGER.info("sent: $packet")

        PacketHandlerRegistries.SERVER
            .getPostOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetSending(event: PacketSendingEvent) {
        ProxyServer.LOGGER.info("sending: ${event.packet.javaClass.simpleName}")

        val packet = event.packet
        val session = event.session

        PacketHandlerRegistries.SERVER
            .getOutgoing(packet::class)?.process(session, packet)
    }

    override fun disconnected(event: DisconnectedEvent) {
        println("User disconnect")
        currentSession = null
    }

}