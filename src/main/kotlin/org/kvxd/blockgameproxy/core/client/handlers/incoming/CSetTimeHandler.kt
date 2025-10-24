package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundSetTimePacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CSetTimeHandler : IncomingPacketHandler<ClientboundSetTimePacket> {

    override fun handle(session: Session, packet: ClientboundSetTimePacket): ClientboundSetTimePacket {
        Cache.WORLD.gameTime = packet.gameTime
        Cache.WORLD.dayTime = packet.dayTime

        return packet
    }

}