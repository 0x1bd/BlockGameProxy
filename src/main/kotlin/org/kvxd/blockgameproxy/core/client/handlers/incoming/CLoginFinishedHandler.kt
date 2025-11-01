package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket

class CLoginFinishedHandler : IncomingPacketHandler<ClientboundLoginFinishedPacket> {

    override fun process(
        session: Session,
        packet: ClientboundLoginFinishedPacket
    ): ClientboundLoginFinishedPacket {
        session.send(ServerboundLoginAcknowledgedPacket())

        session.switchState(ProtocolState.CONFIGURATION)

        return packet
    }

    override val shouldForward: Boolean = false

}