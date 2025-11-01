package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CPlayerPosHandler : IncomingPacketHandler<ClientboundPlayerPositionPacket> {

    override fun process(session: Session, packet: ClientboundPlayerPositionPacket): ClientboundPlayerPositionPacket {
        with(PlayerCache) {
            position = packet.position
            positionDelta = packet.deltaMovement
            yaw = packet.yRot
            pitch = packet.xRot
            positionFlags = packet.relatives
        }

        return packet
    }

}