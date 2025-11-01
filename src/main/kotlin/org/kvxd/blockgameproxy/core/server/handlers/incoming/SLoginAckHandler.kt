package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState

class SLoginAckHandler : IncomingPacketHandler<ServerboundLoginAcknowledgedPacket> {

    override fun process(
        session: Session,
        packet: ServerboundLoginAcknowledgedPacket
    ): ServerboundLoginAcknowledgedPacket {
        session.switchState(ProtocolState.CONFIGURATION)

        session.send(ClientboundSelectKnownPacks(RegistryCache.knownPacks))

        RegistryCache.registryData.forEach { data ->
            session.send(ClientboundRegistryDataPacket(data.key, data.entries))
        }

        session.send(RegistryCache.tagsPacket!!)

        session.send(ClientboundFinishConfigurationPacket())

        return packet
    }

    override val shouldForward: Boolean = false

}