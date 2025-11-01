package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundPlayerInfoRemovePacket
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundPlayerInfoUpdatePacket
import org.kvxd.blockgameproxy.core.cache.caches.TabListCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistry

object CPlayerInfoHandlers {

    fun PacketHandlerRegistry.registerTabListHandlers() {
        registerIncoming(CPlayerInfoUpdateHandler())
        registerIncoming(CPlayerInfoRemoveHandler())
    }

}

class CPlayerInfoUpdateHandler : IncomingPacketHandler<ClientboundPlayerInfoUpdatePacket> {

    override fun process(
        session: Session,
        packet: ClientboundPlayerInfoUpdatePacket
    ): ClientboundPlayerInfoUpdatePacket {
        TabListCache.handleUpdatePacket(packet)

        return packet
    }

}

class CPlayerInfoRemoveHandler : IncomingPacketHandler<ClientboundPlayerInfoRemovePacket> {

    override fun process(
        session: Session,
        packet: ClientboundPlayerInfoRemovePacket
    ): ClientboundPlayerInfoRemovePacket {
        TabListCache.handleRemovePacket(packet)

        return packet
    }

}