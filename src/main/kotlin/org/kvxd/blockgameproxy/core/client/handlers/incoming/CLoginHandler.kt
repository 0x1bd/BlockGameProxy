package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundUpdateEnabledFeaturesPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundLoginPacket
import org.kvxd.blockgameproxy.core.shared.SharedData

class CLoginHandler : IncomingPacketHandler<ClientboundLoginPacket> {

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