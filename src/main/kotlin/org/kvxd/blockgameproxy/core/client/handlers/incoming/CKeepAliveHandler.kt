package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundKeepAlivePacket
import org.geysermc.mcprotocollib.protocol.packet.common.serverbound.ServerboundKeepAlivePacket

class CKeepAliveHandler : IncomingPacketHandler<ClientboundKeepAlivePacket> {

    override fun handle(
        session: Session,
        packet: ClientboundKeepAlivePacket
    ): ClientboundKeepAlivePacket {
        session.send(ServerboundKeepAlivePacket(packet.pingId))

        return packet
    }
}