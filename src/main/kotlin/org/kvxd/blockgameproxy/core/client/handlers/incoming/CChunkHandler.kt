package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLevelChunkWithLightPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.CacheSet
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CChunkHandler : IncomingPacketHandler<ClientboundLevelChunkWithLightPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLevelChunkWithLightPacket
    ): ClientboundLevelChunkWithLightPacket {
        CacheSet.Chunk.handleChunkPacket(packet)

        return packet
    }

}