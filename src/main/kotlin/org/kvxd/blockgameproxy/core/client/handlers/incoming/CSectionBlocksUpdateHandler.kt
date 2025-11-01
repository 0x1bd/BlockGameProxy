package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSectionBlocksUpdatePacket
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CSectionBlocksUpdateHandler : IncomingPacketHandler<ClientboundSectionBlocksUpdatePacket> {

    override fun process(
        session: Session,
        packet: ClientboundSectionBlocksUpdatePacket
    ): ClientboundSectionBlocksUpdatePacket {
        ChunkCache.handleSectionBlocksUpdate(packet)

        return packet
    }

}