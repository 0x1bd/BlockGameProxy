package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.kvxd.blockgameproxy.core.cache.Cache

class CUpdateTagsHandler : IncomingPacketHandler<ClientboundUpdateTagsPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundUpdateTagsPacket
    ): ClientboundUpdateTagsPacket {
        Cache.REGISTRY.tagsPacket = packet

        return packet
    }
}