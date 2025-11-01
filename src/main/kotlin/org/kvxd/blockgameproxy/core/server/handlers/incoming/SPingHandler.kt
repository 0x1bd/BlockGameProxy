package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ping.clientbound.ClientboundPongResponsePacket
import org.geysermc.mcprotocollib.protocol.packet.ping.serverbound.ServerboundPingRequestPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class SPingHandler : IncomingPacketHandler<ServerboundPingRequestPacket> {

    override fun process(session: Session, packet: ServerboundPingRequestPacket): ServerboundPingRequestPacket {
        session.send(ClientboundPongResponsePacket(packet.pingTime))

        return packet
    }

    override val shouldForward: Boolean = false

}