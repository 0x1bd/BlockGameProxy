package org.kvxd.blockgameproxy.core.server.handlers.incoming

import net.kyori.adventure.text.minimessage.MiniMessage
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.data.status.PlayerInfo
import org.geysermc.mcprotocollib.protocol.data.status.ServerStatusInfo
import org.geysermc.mcprotocollib.protocol.data.status.VersionInfo
import org.geysermc.mcprotocollib.protocol.packet.status.clientbound.ClientboundStatusResponsePacket
import org.geysermc.mcprotocollib.protocol.packet.status.serverbound.ServerboundStatusRequestPacket
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.server.ProxyServer

class SStatusHandler : IncomingPacketHandler<ServerboundStatusRequestPacket> {

    override fun process(session: Session, packet: ServerboundStatusRequestPacket): ServerboundStatusRequestPacket {
        session.send(ClientboundStatusResponsePacket(buildStatusInfo()))

        return packet
    }

    override val shouldForward: Boolean = false

}

fun buildStatusInfo(): ServerStatusInfo {
    val mm = MiniMessage.miniMessage()
    val accepts = ProxyServer.acceptsConnections
    val statusText = if (accepts) {
        "<green>Waiting for a connection</green>"
    } else {
        "<red>Proxy in use</red>"
    }

    val motd = mm.deserialize(
        """
        <gradient:gold:yellow><bold>BlockGameProxy</bold></gradient>
        $statusText
        """.trimIndent()
    )

    val onlineCount = if (accepts) 0 else 1

    return ServerStatusInfo(
        motd,
        PlayerInfo(1, onlineCount, emptyList()),
        VersionInfo(
            ProxyServer.codec.minecraftVersion,
            ProxyServer.codec.protocolVersion
        ),
        byteArrayOf(),
        false
    )
}
