package dev.ogkush32.kushproxy

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.data.ProtocolState
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.compression.ZlibCompression

object PacketHandler {

    fun handleServerToPlayerPacket(playerSession: Session, packet: Packet) {
        println("Server → Proxy → Player: ${packet.javaClass.simpleName}")

        when (packet) {
            is ClientboundLoginCompressionPacket -> {
                playerSession.send(packet)

                val threshold = packet.threshold
                if (threshold >= 0) {
                    playerSession.setCompression(CompressionConfig(threshold, ZlibCompression(), true))
                }
            }

            is ClientboundLoginFinishedPacket -> {
                playerSession.send(packet)
                playerSession.switchInboundState {
                    playerSession.packetProtocol.inboundState = ProtocolState.CONFIGURATION
                }
                playerSession.switchOutboundState {
                    playerSession.packetProtocol.outboundState = ProtocolState.CONFIGURATION
                }
            }

            else -> {
                playerSession.send(packet)
            }
        }
    }
}