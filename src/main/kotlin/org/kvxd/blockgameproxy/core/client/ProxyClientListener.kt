package org.kvxd.blockgameproxy.core.client

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.ConnectedEvent
import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.PacketErrorEvent
import org.geysermc.mcprotocollib.network.event.session.PacketSendingEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.data.handshake.HandshakeIntent
import org.geysermc.mcprotocollib.protocol.packet.handshake.serverbound.ClientIntentionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundHelloPacket
import org.kvxd.blockgameproxy.config.config
import org.kvxd.blockgameproxy.core.getState
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries
import org.kvxd.blockgameproxy.core.server.ProxyServer
import org.kvxd.blockgameproxy.core.switchState
import java.util.*

class ProxyClientListener : SessionAdapter() {

    override fun connected(event: ConnectedEvent) {
        ProxyClient.LOGGER.info("Connected")

        with(ProxyClient) {
            send(
                ClientIntentionPacket(
                    ProxyClient.protocol.codec.protocolVersion,
                    config.targetServer.host,
                    config.targetServer.port,
                    HandshakeIntent.LOGIN
                )
            )

            event.session.switchState(ProtocolState.LOGIN)

            send(ServerboundHelloPacket(config.profileName, UUID.randomUUID()))
        }
    }

    override fun packetReceived(session: Session, packet: Packet) {
        ProxyClient.LOGGER.debug("Packet received: {}", packet)

        val handler = PacketHandlerRegistries.CLIENT
            .getIncoming(packet::class)

        val shouldForward = handler?.shouldForward == true || handler == null

        val processedPacket = handler?.process(session, packet) ?: packet

        if (shouldForward &&
            ProxyServer.currentSession != null &&
            ProxyServer.currentSession?.getState() == ProtocolState.GAME
        ) {
            ProxyServer.currentSession?.send(processedPacket)
        }
    }

    override fun packetSent(session: Session, packet: Packet) {
        ProxyClient.LOGGER.debug("Packet sent: {}", packet)

        PacketHandlerRegistries.CLIENT
            .getPostOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetSending(event: PacketSendingEvent) {
        val packet = event.packet
        val session = event.session

        PacketHandlerRegistries.CLIENT
            .getOutgoing(packet::class)?.process(session, packet)
    }

    override fun packetError(event: PacketErrorEvent) {
        ProxyClient.LOGGER.error("Packet Error: ${event.cause.message}")
        event.cause.printStackTrace()
    }

    override fun disconnected(event: DisconnectedEvent) {
        ProxyClient.LOGGER.info("Disconnected")
    }

}