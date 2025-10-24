package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.ConnectedEvent
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.kvxd.blockgameproxy.core.ControlManager
import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries

class ServerSessionListener : SessionAdapter() {

    override fun connected(event: ConnectedEvent) {
        ProxyServer.LOGGER.debug("Server Session established: ${event.session.remoteAddress}")

        ProxyServer.currentSession = event.session

        ControlManager.transferToServerSession()
    }

    override fun packetReceived(session: Session, packet: Packet) {
        ProxyServer.LOGGER.info("Server Session received: $packet")

        val handler = PacketHandlerRegistries.SERVER
            .getIncoming(packet::class)

        if (handler != null)
            handler.handle(session, packet)
        else {
            if (ControlManager.isControlledByServer()) {
                ProxyClient.send(packet)
            }
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        ProxyServer.LOGGER.info("Server Session sent: $packet")

        PacketHandlerRegistries.SERVER
            .getPostOutgoing(packet::class)?.handle(session, packet)
    }

    override fun packetSending(event: PacketSendingEvent) {
        ProxyServer.LOGGER.info("Server Session sending: ${event.packet.javaClass.simpleName}")

        val packet = event.packet
        val session = event.session

        PacketHandlerRegistries.SERVER
            .getOutgoing(packet::class)?.handle(session, packet)
    }

    override fun disconnected(event: DisconnectedEvent) {
        ProxyServer.currentSession = null

        ControlManager.transferToClient()
    }

}