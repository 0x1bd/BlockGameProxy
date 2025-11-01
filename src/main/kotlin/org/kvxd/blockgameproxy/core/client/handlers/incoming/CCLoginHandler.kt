package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.cache.caches.LoginCache
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CCLoginHandler : IncomingPacketHandler<ClientboundLoginPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLoginPacket
    ): ClientboundLoginPacket {
        LoginCache.entityId = packet.entityId
        LoginCache.spawnInfo = packet.commonPlayerSpawnInfo

        ChunkCache.worldNames = packet.worldNames
        return packet
    }

    override val shouldForward: Boolean = false
}