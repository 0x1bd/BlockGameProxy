package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerAbilitiesPacket
import org.kvxd.blockgameproxy.core.cache.Cache

class CPlayerAbilitiesHandler : IncomingPacketHandler<ClientboundPlayerAbilitiesPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundPlayerAbilitiesPacket
    ): ClientboundPlayerAbilitiesPacket {
        with(Cache.PLAYER) {
            invincible = packet.isInvincible
            canFly = packet.isCanFly
            flying = packet.isFlying
            creative = packet.isCreative
            flySpeed = packet.flySpeed
            walkSpeed = packet.walkSpeed
        }

        return packet
    }
}