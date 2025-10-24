package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.auth.GameProfile
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.crypt.AESEncryption
import org.geysermc.mcprotocollib.network.crypt.EncryptionConfig
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundKeyPacket
import org.kvxd.blockgameproxy.BlockGameProxy
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import java.util.*

class SKeyHandler : IncomingPacketHandler<ServerboundKeyPacket> {

    override fun handle(session: Session, packet: ServerboundKeyPacket): ServerboundKeyPacket {
        val privateKey = BlockGameProxy.KEY_PAIR.private

        check(BlockGameProxy.CHALLENGE.contentEquals(packet.getEncryptedChallenge(privateKey))) { "Protocol error" }

        val key = packet.getSecretKey(privateKey)
        session.setEncryption(EncryptionConfig(AESEncryption(key)))

        println("encryption enabled on server session")

        session.send(
            ClientboundLoginFinishedPacket(
                GameProfile(UUID.randomUUID(), "Blocktest")
            )
        )

        return packet
    }

}