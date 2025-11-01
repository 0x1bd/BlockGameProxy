package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundBlockEntityDataPacket
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CBlockEntityDataHandler : IncomingPacketHandler<ClientboundBlockEntityDataPacket> {

    override fun process(session: Session, packet: ClientboundBlockEntityDataPacket): ClientboundBlockEntityDataPacket {
        ChunkCache.handleBlockEntityData(packet)

        return packet
    }

}