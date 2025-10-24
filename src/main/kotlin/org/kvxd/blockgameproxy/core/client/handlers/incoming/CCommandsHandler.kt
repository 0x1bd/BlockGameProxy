package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.ingame.clientbound.ClientboundCommandsPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.shared.SharedData

class CCommandsHandler : IncomingPacketHandler<ClientboundCommandsPacket> {

    override fun handle(session: Session, packet: ClientboundCommandsPacket): ClientboundCommandsPacket {
        SharedData.commandNodes = packet.nodes
        SharedData.firstCommandNodeIndex = packet.firstNodeIndex

        return packet
    }

}