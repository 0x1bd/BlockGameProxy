package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundHelloPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundHelloPacket
import org.kvxd.blockgameproxy.BlockGameProxy
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler

class SHelloHandler : IncomingPacketHandler<ServerboundHelloPacket> {

    override fun handle(session: Session, packet: ServerboundHelloPacket): ServerboundHelloPacket {
        session.send(ClientboundHelloPacket(
            "",
            BlockGameProxy.KEY_PAIR.public,
            BlockGameProxy.CHALLENGE,
            false
        ))

        return packet
    }

}