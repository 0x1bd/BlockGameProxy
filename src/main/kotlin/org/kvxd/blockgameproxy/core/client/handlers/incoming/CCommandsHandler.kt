package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.kvxd.blockgameproxy.core.cache.caches.CommandCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CCommandsHandler : IncomingPacketHandler<ClientboundCommandsPacket> {

    override fun process(session: Session, packet: ClientboundCommandsPacket): ClientboundCommandsPacket {
        CommandCache.handlePacket(packet)

        return packet
    }

}