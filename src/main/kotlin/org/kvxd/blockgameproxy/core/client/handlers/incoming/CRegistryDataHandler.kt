package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache
import org.kvxd.blockgameproxy.core.cache.caches.RegistryData
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CRegistryDataHandler : IncomingPacketHandler<ClientboundRegistryDataPacket> {

    override fun process(
        session: Session,
        packet: ClientboundRegistryDataPacket
    ): ClientboundRegistryDataPacket {
        RegistryCache.registryData += RegistryData(
            packet.registry,
            packet.entries
        )

        return packet
    }

    override val shouldForward: Boolean = false
}