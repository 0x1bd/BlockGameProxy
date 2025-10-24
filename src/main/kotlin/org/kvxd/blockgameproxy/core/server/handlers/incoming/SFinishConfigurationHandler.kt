package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.shared.SharedData
import org.kvxd.blockgameproxy.core.switchState

class SFinishConfigurationHandler : IncomingPacketHandler<ServerboundFinishConfigurationPacket> {

    override fun handle(
        session: Session,
        packet: ServerboundFinishConfigurationPacket
    ): ServerboundFinishConfigurationPacket {
        session.switchState(ProtocolState.GAME)

        session.send(ClientboundLoginPacket(
            SharedData.loginData!!.entityId,
            false,
            SharedData.loginData!!.worldNames,
            20,
            16,
            16,
            false,
            false,
            false,
            SharedData.loginData!!.commonPlayerSpawnInfo,
            false
        ))

        session.send(ClientboundCommandsPacket(SharedData.commandNodes, SharedData.firstCommandNodeIndex))

        return packet
    }

}