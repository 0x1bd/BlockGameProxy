package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundSetHealthPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CSetHealthHandler : IncomingPacketHandler<ClientboundSetHealthPacket> {

    override fun handle(session: Session, packet: ClientboundSetHealthPacket): ClientboundSetHealthPacket {
        Cache.PLAYER.health = packet.health
        Cache.PLAYER.food = packet.food
        Cache.PLAYER.saturation = packet.saturation

        return packet
    }

}