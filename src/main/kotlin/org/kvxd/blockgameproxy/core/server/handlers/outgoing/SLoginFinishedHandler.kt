package org.kvxd.blockgameproxy.core.server.handlers.outgoing

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.kvxd.blockgameproxy.core.handler.OutgoingPacketHandler
import org.kvxd.blockgameproxy.core.server.ProxyServer

class SLoginFinishedHandler : OutgoingPacketHandler<ClientboundLoginFinishedPacket> {

    override fun process(
        session: Session, packet: ClientboundLoginFinishedPacket
    ): ClientboundLoginFinishedPacket {
        synchronized(ProxyServer) {
            if (ProxyServer.currentSession == null) {
                ProxyServer.currentSession = session
                ProxyServer.LOGGER.info("Session ${session.remoteAddress} accepted as currentSession — enabling live forwarding")
            } else {
                ProxyServer.LOGGER.warn("Login finished for session ${session.remoteAddress} but someone is already connected; disconnecting")
                try {
                    session.disconnect("Proxy already in use")
                } catch (t: Throwable) {
                    ProxyServer.LOGGER.warn("Failed to disconnect extra session", t)
                }
            }
        }

        return packet
    }
}
