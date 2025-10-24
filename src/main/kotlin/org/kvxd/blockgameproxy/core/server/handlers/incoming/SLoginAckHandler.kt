package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState

class SLoginAckHandler : IncomingPacketHandler<ServerboundLoginAcknowledgedPacket> {

    override fun handle(
        session: Session,
        packet: ServerboundLoginAcknowledgedPacket
    ): ServerboundLoginAcknowledgedPacket {
        session.switchState(ProtocolState.CONFIGURATION)

        session.send(ClientboundSelectKnownPacks(Cache.REGISTRY.knownPacks))

        Cache.REGISTRY.registryData.forEach { data ->
            session.send(ClientboundRegistryDataPacket(data.key, data.entries))
        }

        check(Cache.REGISTRY.tagsPacket != null) { "Protocol Error: Tags Cache is invalid" }

        session.send(Cache.REGISTRY.tagsPacket!!)

        session.send(ClientboundFinishConfigurationPacket())

        return packet
    }

}