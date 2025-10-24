package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks
import org.kvxd.blockgameproxy.core.cache.Cache

class CSelectKnownPacks : IncomingPacketHandler<ClientboundSelectKnownPacks> {

    override fun handle(
        session: Session,
        packet: ClientboundSelectKnownPacks
    ): ClientboundSelectKnownPacks {
        Cache.REGISTRY.knownPacks = packet.knownPacks

        session.send(ServerboundSelectKnownPacks(packet.knownPacks))

        return packet
    }
}