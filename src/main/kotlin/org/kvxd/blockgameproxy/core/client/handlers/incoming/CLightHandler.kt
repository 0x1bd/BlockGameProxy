package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.level.ClientboundLightUpdatePacket
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class CLightHandler : IncomingPacketHandler<ClientboundLightUpdatePacket> {

    override fun process(session: Session, packet: ClientboundLightUpdatePacket): ClientboundLightUpdatePacket {
        ChunkCache.handleLightUpdatePacket(packet)

        return packet
    }

}