package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.cache.Cache

class CCLoginHandler : IncomingPacketHandler<ClientboundLoginPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundLoginPacket
    ): ClientboundLoginPacket {
        with(Cache.LOGIN) {
            entityId = packet.entityId
            worldNames = packet.worldNames
            spawnInfo = packet.commonPlayerSpawnInfo
        }

        return packet
    }
}