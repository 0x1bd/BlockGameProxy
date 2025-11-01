package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLevelChunkWithLightPacket
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CChunkHandler : IncomingPacketHandler<ClientboundLevelChunkWithLightPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLevelChunkWithLightPacket
    ): ClientboundLevelChunkWithLightPacket {
        ChunkCache.handleChunkPacket(packet)

        return packet
    }

}