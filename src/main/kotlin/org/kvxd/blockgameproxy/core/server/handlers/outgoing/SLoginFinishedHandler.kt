package org.kvxd.blockgameproxy.core.server.handlers.outgoing

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.kvxd.blockgameproxy.core.handler.OutgoingPacketHandler
import org.kvxd.blockgameproxy.core.server.ProxyServer

class SLoginFinishedHandler : OutgoingPacketHandler<ClientboundLoginFinishedPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLoginFinishedPacket
    ): ClientboundLoginFinishedPacket {
        ProxyServer.currentSession = session

        return packet
    }
}