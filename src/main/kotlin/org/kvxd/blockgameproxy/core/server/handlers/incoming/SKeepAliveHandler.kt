package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundKeepAlivePacket
import org.geysermc.mcprotocollib.protocol.packet.common.serverbound.ServerboundKeepAlivePacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class SKeepAliveHandler : IncomingPacketHandler<ServerboundKeepAlivePacket> {

    override fun process(session: Session, packet: ServerboundKeepAlivePacket): ServerboundKeepAlivePacket {
        //session.send(ClientboundKeepAlivePacket(
        //    packet.pingId
        //))

        return packet
    }

    override val shouldForward: Boolean = false

}