package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CSelectKnownPacks : IncomingPacketHandler<ClientboundSelectKnownPacks> {

    override fun process(
        session: Session,
        packet: ClientboundSelectKnownPacks
    ): ClientboundSelectKnownPacks {
        RegistryCache.knownPacks = packet.knownPacks

        session.send(ServerboundSelectKnownPacks(packet.knownPacks))

        return packet
    }

    override val shouldForward: Boolean = false
}