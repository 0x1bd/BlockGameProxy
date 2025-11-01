package org.kvxd.blockgameproxy.core.server.handlers.outgoing

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.kvxd.blockgameproxy.core.handler.OutgoingPacketHandler
import org.kvxd.blockgameproxy.core.server.ProxyServer

class SLoginFinishedHandler : OutgoingPacketHandler<ClientboundLoginFinishedPacket> {

    override fun process(
        session: Session, packet: ClientboundLoginFinishedPacket
    ): ClientboundLoginFinishedPacket {
        if (ProxyServer.currentSession == null) {
            ProxyServer.currentSession = session
            ProxyServer.LOGGER.info("User connected: ${session.remoteAddress}")
        } else {
            ProxyServer.LOGGER.warn("User attempted to connect: ${session.remoteAddress}")
            try {
                session.disconnect("Proxy already in use")
            } catch (t: Throwable) {
                ProxyServer.LOGGER.warn("Failed to disconnect user", t)
            }
        }
        
        return packet
    }
}