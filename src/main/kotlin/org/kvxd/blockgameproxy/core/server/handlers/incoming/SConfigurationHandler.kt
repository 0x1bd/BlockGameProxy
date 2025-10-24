package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.game.level.notify.GameEvent
import org.geysermc.mcprotocollib.protocol.data.game.level.notify.GameEventValue
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerPositionPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundGameEventPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.serverbound.ServerboundConfigurationAcknowledgedPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.shared.SharedData
import org.kvxd.blockgameproxy.core.switchState

class SConfigurationHandler : IncomingPacketHandler<ServerboundFinishConfigurationPacket> {

    override fun handle(
        session: Session,
        packet: ServerboundFinishConfigurationPacket
    ): ServerboundFinishConfigurationPacket {
        session.switchState(ProtocolState.GAME)

        val data = SharedData.loginData!!

        session.send(ClientboundLoginPacket(
            data.entityId,
            data.hardcore,
            data.worldNames,
            data.maxPlayers,
            data.viewDistance,
            data.simulationDistance,
            data.reducedDebugInfo,
            data.enableRespawnScreen,
            data.doLimitedCrafting,
            data.commonPlayerSpawnInfo,
            data.enforcesSecureChat
        ))

        session.send(ClientboundPlayerPositionPacket(
            0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0f,
            0f
        ))

        return packet
    }

}