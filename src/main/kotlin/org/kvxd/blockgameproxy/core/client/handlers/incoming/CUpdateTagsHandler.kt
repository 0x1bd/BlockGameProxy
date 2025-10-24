package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundUpdateEnabledFeaturesPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.kvxd.blockgameproxy.core.shared.SharedData

class CUpdateTagsHandler : IncomingPacketHandler<ClientboundUpdateTagsPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundUpdateTagsPacket
    ): ClientboundUpdateTagsPacket {
        val packetTags = packet.tags

        SharedData.tags.putAll(packetTags)

        return packet
    }
}