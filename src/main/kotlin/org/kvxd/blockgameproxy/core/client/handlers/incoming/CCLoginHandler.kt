package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.shared.SharedData

class CCLoginHandler : IncomingPacketHandler<ClientboundLoginPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundLoginPacket
    ): ClientboundLoginPacket {
        val data = SharedData.LoginData(
            packet.entityId,
            packet.isHardcore,
            packet.worldNames,
            packet.maxPlayers,
            packet.viewDistance,
            packet.simulationDistance,
            packet.isReducedDebugInfo,
            packet.isEnableRespawnScreen,
            packet.isDoLimitedCrafting,
            packet.commonPlayerSpawnInfo,
            packet.isEnforcesSecureChat
        )

        SharedData.loginData = data

        return packet
    }
}