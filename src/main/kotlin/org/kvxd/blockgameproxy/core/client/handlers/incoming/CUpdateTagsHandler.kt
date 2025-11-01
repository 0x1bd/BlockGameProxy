package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CUpdateTagsHandler : IncomingPacketHandler<ClientboundUpdateTagsPacket> {

    override fun process(
        session: Session,
        packet: ClientboundUpdateTagsPacket
    ): ClientboundUpdateTagsPacket {
        RegistryCache.tagsPacket = packet

        return packet
    }

    override val shouldForward: Boolean = false
}