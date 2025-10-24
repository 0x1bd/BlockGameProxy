package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState
import org.kvxd.blockgameproxy.core.toProtocolState

class SIntentHandler : IncomingPacketHandler<ClientIntentionPacket> {

    override fun handle(session: Session, packet: ClientIntentionPacket): ClientIntentionPacket {
        val state = packet.intent.toProtocolState()

        session.switchState(state)

        return packet
    }

}