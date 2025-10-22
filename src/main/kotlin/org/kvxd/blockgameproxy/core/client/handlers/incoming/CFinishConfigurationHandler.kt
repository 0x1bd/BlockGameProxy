package org.kvxd.blockgameproxy.core.client.handlers.incoming

import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.switchState
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.serverbound.ServerboundFinishConfigurationPacket

class CFinishConfigurationHandler : IncomingPacketHandler<ClientboundFinishConfigurationPacket> {

    override fun handle(
        session: Session,
        packet: ClientboundFinishConfigurationPacket
    ): ClientboundFinishConfigurationPacket {
        session.send(ServerboundFinishConfigurationPacket())
        session.switchState(ProtocolState.GAME)

        return packet
    }
}