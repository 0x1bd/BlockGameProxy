package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundSelectKnownPacks

class CSelectKnownPacks : IncomingPacketHandler<ClientboundSelectKnownPacks> {

    override fun handle(
        session: Session,
        packet: ClientboundSelectKnownPacks
    ): ClientboundSelectKnownPacks {
        session.send(ServerboundSelectKnownPacks(emptyList()))

        return packet
    }
}