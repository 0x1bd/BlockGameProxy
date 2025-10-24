package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.shared.SharedData

class SKnownPacksHandler : IncomingPacketHandler<ServerboundSelectKnownPacks> {

    override fun handle(session: Session, packet: ServerboundSelectKnownPacks): ServerboundSelectKnownPacks {
        SharedData.registryData.forEach { registryData ->
            session.send(ClientboundRegistryDataPacket(registryData.key, registryData.entries))
        }

        session.send(ClientboundFinishConfigurationPacket())

        return packet
    }

}