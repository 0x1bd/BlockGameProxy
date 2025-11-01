package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.kvxd.blockgameproxy.core.cache.CacheSet
import org.kvxd.blockgameproxy.core.cache.caches.RegistryData

class CRegistryDataHandler : IncomingPacketHandler<ClientboundRegistryDataPacket> {

    override fun process(
        session: Session,
        packet: ClientboundRegistryDataPacket
    ): ClientboundRegistryDataPacket {
        CacheSet.Registry.registryData += RegistryData(
            packet.registry,
            packet.entries
        )

        return packet
    }

    override val shouldForward: Boolean = false
}