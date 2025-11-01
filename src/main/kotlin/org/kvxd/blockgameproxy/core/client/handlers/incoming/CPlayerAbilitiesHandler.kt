package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerAbilitiesPacket
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CPlayerAbilitiesHandler : IncomingPacketHandler<ClientboundPlayerAbilitiesPacket> {

    override fun process(
        session: Session,
        packet: ClientboundPlayerAbilitiesPacket
    ): ClientboundPlayerAbilitiesPacket {
        with(PlayerCache) {
            isInvincible = packet.isInvincible
            canFly = packet.isCanFly
            flying = packet.isFlying
            inCreative = packet.isCreative
            flySpeed = packet.flySpeed
            walkSpeed = packet.walkSpeed
        }

        return packet
    }
}