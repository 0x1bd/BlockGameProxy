package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CCommandsHandler : IncomingPacketHandler<ClientboundCommandsPacket> {

    override fun handle(session: Session, packet: ClientboundCommandsPacket): ClientboundCommandsPacket {
        Cache.COMMAND.nodes = packet.nodes
        Cache.COMMAND.firstNodeIdx = packet.firstNodeIndex

        return packet
    }

}