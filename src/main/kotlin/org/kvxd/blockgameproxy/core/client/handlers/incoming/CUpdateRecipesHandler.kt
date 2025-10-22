package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundUpdateEnabledFeaturesPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundUpdateRecipesPacket
import org.kvxd.blockgameproxy.core.shared.SharedData

class CUpdateRecipesHandler : IncomingPacketHandler<ClientboundUpdateRecipesPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundUpdateRecipesPacket
    ): ClientboundUpdateRecipesPacket {
        SharedData.itemSets = packet.itemSets
        SharedData.stonecutterRecipes = packet.stonecutterRecipes

        return packet
    }
}