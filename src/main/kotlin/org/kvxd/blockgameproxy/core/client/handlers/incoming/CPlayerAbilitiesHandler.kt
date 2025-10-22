package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundUpdateEnabledFeaturesPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.entity.player.ClientboundPlayerAbilitiesPacket
import org.kvxd.blockgameproxy.core.shared.SharedData

class CPlayerAbilitiesHandler : IncomingPacketHandler<ClientboundPlayerAbilitiesPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundPlayerAbilitiesPacket
    ): ClientboundPlayerAbilitiesPacket {
        val data = SharedData.PlayerData(
            packet.isInvincible,
            packet.isCanFly,
            packet.isFlying,
            packet.isCreative,
            packet.flySpeed,
            packet.walkSpeed
        )

        SharedData.playerData = data

        return packet
    }
}