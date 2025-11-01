package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundBlockUpdatePacket
import org.kvxd.blockgameproxy.core.cache.CacheSet
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CBlockUpdateHandler : IncomingPacketHandler<ClientboundBlockUpdatePacket> {

    override fun process(session: Session, packet: ClientboundBlockUpdatePacket): ClientboundBlockUpdatePacket {
        CacheSet.Chunk
            .handleBlockUpdate(packet)

        return packet
    }

}