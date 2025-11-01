package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.kvxd.blockgameproxy.core.cache.CacheSet

class CUpdateTagsHandler : IncomingPacketHandler<ClientboundUpdateTagsPacket> {

    override fun process(
        session: Session,
        packet: ClientboundUpdateTagsPacket
    ): ClientboundUpdateTagsPacket {
        CacheSet.Registry.tagsPacket = packet

        return packet
    }

    override val shouldForward: Boolean = false
}