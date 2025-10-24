package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundUpdateTagsPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundFinishConfigurationPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundRegistryDataPacket
import org.geysermc.mcprotocollib.protocol.packet.configuration.clientbound.ClientboundSelectKnownPacks
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.shared.SharedData
import org.kvxd.blockgameproxy.core.switchState

class SCLoginHandler : IncomingPacketHandler<ServerboundLoginAcknowledgedPacket> {

    override fun handle(
        session: Session,
        packet: ServerboundLoginAcknowledgedPacket
    ): ServerboundLoginAcknowledgedPacket {
        session.switchState(ProtocolState.CONFIGURATION)

        session.send(ClientboundSelectKnownPacks(SharedData.knownPacks))

        session.send(ClientboundFinishConfigurationPacket())

        return packet
    }


}