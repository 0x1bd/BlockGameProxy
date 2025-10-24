package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.caches.RegistryData

class CRegistryDataHandler : IncomingPacketHandler<ClientboundRegistryDataPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundRegistryDataPacket
    ): ClientboundRegistryDataPacket {
        Cache.REGISTRY.registryData += RegistryData(
            packet.registry,
            packet.entries
        )

        return packet
    }
}