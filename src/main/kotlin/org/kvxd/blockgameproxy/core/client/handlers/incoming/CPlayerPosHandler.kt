package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CPlayerPosHandler : IncomingPacketHandler<ClientboundPlayerPositionPacket> {

    override fun handle(session: Session, packet: ClientboundPlayerPositionPacket): ClientboundPlayerPositionPacket {
        with(Cache.PLAYER.position) {
            position = packet.position
            delta = packet.deltaMovement
            yaw = packet.yRot
            pitch = packet.xRot
            flags = packet.relatives
        }

        return packet
    }

}