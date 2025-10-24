package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundSetExperiencePacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CSetExperienceHandler : IncomingPacketHandler<ClientboundSetExperiencePacket> {

    override fun handle(session: Session, packet: ClientboundSetExperiencePacket): ClientboundSetExperiencePacket {
        Cache.PLAYER.exp = packet.experience
        Cache.PLAYER.expLevel = packet.level
        Cache.PLAYER.totalExp = packet.totalExperience

        return packet
    }

}