package dev.ogkush32.kushproxy.listeners

import dev.ogkush32.kushproxy.ProxyClient
import dev.ogkush32.kushproxy.switchState
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.event.session.*
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.compression.ZlibCompression
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundLoginAcknowledgedPacket

class ClientSessionListener(private val client: ProxyClient) : SessionAdapter() {

    override fun connected(event: ConnectedEvent) {
        println("Connected to target server at ${event.session.remoteAddress}")
        client.setCurrentSession(event.session)
    }

    override fun disconnected(event: DisconnectedEvent) {
        println("Disconnected from target server at ${event.session.remoteAddress}")
        client.setCurrentSession(null)
    }

    override fun packetReceived(session: Session, packet: Packet) {
        println("ProxyClient received from server: ${packet.javaClass.simpleName}")

        when (packet) {
            is ClientboundLoginCompressionPacket -> {
                client.onPacketReceived?.invoke(packet)
                val threshold = packet.threshold
                if (threshold >= 0) {
                    session.setCompression(CompressionConfig(threshold, ZlibCompression(), false))
                }
            }

            else -> {
                client.onPacketReceived?.invoke(packet)
            }
        }
    }

    override fun packetSending(event: PacketSendingEvent) {
        println("ProxyClient forwarding packet to server: ${event.packet.javaClass.simpleName}")
    }

    override fun packetSent(session: Session, packet: Packet) {
        println("ProxyClient forwarded packet to server: ${packet.javaClass.simpleName}")

        when (packet) {
            is ServerboundLoginAcknowledgedPacket -> {
                session.switchState(ProtocolState.CONFIGURATION)

                client.setNextState(ProtocolState.CONFIGURATION)
            }
        }
    }
}
