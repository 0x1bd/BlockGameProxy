package dev.ogkush32.kushproxy

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.packet.Packet
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket
import org.geysermc.mcprotocollib.network.compression.CompressionConfig
import org.geysermc.mcprotocollib.network.compression.ZlibCompression

object PacketHandler {

    fun handleServerToPlayerPacket(playerSession: Session, packet: Packet) {
        println("Server sent: ${packet.javaClass.simpleName}")

        when (packet) {
            is ClientboundLoginCompressionPacket -> {
                playerSession.send(packet)

                val threshold = packet.threshold
                if (threshold >= 0) {
                    playerSession.setCompression(CompressionConfig(threshold, ZlibCompression(), true))
                }
            }

            else -> playerSession.send(packet)
        }
    }
}
