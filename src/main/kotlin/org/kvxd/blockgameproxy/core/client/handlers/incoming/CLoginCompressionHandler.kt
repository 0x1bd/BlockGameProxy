package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.setCompressionThreshold
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket

class CLoginCompressionHandler : IncomingPacketHandler<ClientboundLoginCompressionPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLoginCompressionPacket
    ): ClientboundLoginCompressionPacket {
        session.setCompressionThreshold(packet.threshold)

        return packet
    }

    override val shouldForward: Boolean = false
}