package org.kvxd.blockgameproxy.core.server.handlers.incoming

import net.kyori.adventure.text.Component
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.status.PlayerInfo
import org.geysermc.mcprotocollib.protocol.data.status.ServerStatusInfo
import org.geysermc.mcprotocollib.protocol.data.status.VersionInfo
import org.geysermc.mcprotocollib.protocol.packet.status.clientbound.ClientboundStatusResponsePacket
import org.geysermc.mcprotocollib.protocol.packet.status.serverbound.ServerboundStatusRequestPacket
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.server.ProxyServer

class SStatusHandler : IncomingPacketHandler<ServerboundStatusRequestPacket> {

    companion object {

        val statusInfo = ServerStatusInfo(
            Component.text("Blockgame Proxy").appendNewline()
                .append(Component.text("@${config.targetServer.host}:${config.targetServer.port}")),
            PlayerInfo(0, 0, emptyList()),
            VersionInfo(ProxyServer.getCodec().minecraftVersion, ProxyServer.getCodec().protocolVersion),
            byteArrayOf(),
            false
        )
    }

    override fun process(session: Session, packet: ServerboundStatusRequestPacket): ServerboundStatusRequestPacket {
        session.send(ClientboundStatusResponsePacket(statusInfo))

        return packet
    }

    override val shouldForward: Boolean = false

}