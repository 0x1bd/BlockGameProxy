package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundUpdateEnabledFeaturesPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.kvxd.blockgameproxy.core.shared.SharedData

class CRegistryDataHandler : IncomingPacketHandler<ClientboundRegistryDataPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundRegistryDataPacket
    ): ClientboundRegistryDataPacket {
        val data = SharedData.RegistryData(
            packet.registry,
            packet.entries
        )

        SharedData.registryData += data

        return packet
    }
}