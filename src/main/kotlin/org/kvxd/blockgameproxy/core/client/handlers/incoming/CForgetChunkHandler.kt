package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundForgetLevelChunkPacket
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CForgetChunkHandler : IncomingPacketHandler<ClientboundForgetLevelChunkPacket> {

    override fun process(
        session: Session,
        packet: ClientboundForgetLevelChunkPacket
    ): ClientboundForgetLevelChunkPacket {
        ChunkCache.unloadChunk(packet.x, packet.z)

        return packet
    }

}