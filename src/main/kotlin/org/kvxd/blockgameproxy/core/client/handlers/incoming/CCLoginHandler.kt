package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.CacheSet

class CCLoginHandler : IncomingPacketHandler<ClientboundLoginPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLoginPacket
    ): ClientboundLoginPacket {
        CacheSet.Login.entityId = packet.entityId
        CacheSet.Login.spawnInfo = packet.commonPlayerSpawnInfo

        CacheSet.Chunk.worldNames = packet.worldNames
        return packet
    }

    override val shouldForward: Boolean = false
}