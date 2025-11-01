package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.player.ServerboundMovePlayerPosRotPacket
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class SMovePlayerPosHandler : IncomingPacketHandler<ServerboundMovePlayerPosPacket> {

    override fun process(session: Session, packet: ServerboundMovePlayerPosPacket): ServerboundMovePlayerPosPacket {
        PlayerCache.position =
            Vector3d.from(packet.x, packet.y, packet.z)

        return packet
    }

}

class SMovePlayerPosRotHandler : IncomingPacketHandler<ServerboundMovePlayerPosRotPacket> {

    override fun process(
        session: Session,
        packet: ServerboundMovePlayerPosRotPacket
    ): ServerboundMovePlayerPosRotPacket {
        PlayerCache.position =
            Vector3d.from(packet.x, packet.y, packet.z)

        PlayerCache.yaw = packet.yaw
        PlayerCache.pitch = packet.pitch

        return packet
    }

}