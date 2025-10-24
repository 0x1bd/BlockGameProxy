package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState

class SFinishConfigurationHandler : IncomingPacketHandler<ServerboundFinishConfigurationPacket> {

    override fun handle(
        session: Session,
        packet: ServerboundFinishConfigurationPacket
    ): ServerboundFinishConfigurationPacket {
        session.switchState(ProtocolState.GAME)

        session.send(
            ClientboundLoginPacket(
                Cache.LOGIN.entityId,
                false,
                Cache.LOGIN.worldNames,
                20,
                16,
                16,
                false,
                false,
                false,
                Cache.LOGIN.spawnInfo,
                false
            )
        )

        session.send(ClientboundCommandsPacket(Cache.COMMAND.nodes, Cache.COMMAND.firstNodeIdx))

        with(Cache.PLAYER.position) {
            session.send(
                ClientboundPlayerPositionPacket(
                    0,
                    position,
                    delta,
                    yaw,
                    pitch,
                    flags
                )
            )
        }

        return packet
    }

}