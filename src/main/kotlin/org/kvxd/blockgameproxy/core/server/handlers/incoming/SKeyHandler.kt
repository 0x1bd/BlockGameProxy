package org.kvxd.blockgameproxy.core.server.handlers.incoming

import org.geysermc.mcprotocollib.auth.GameProfile
import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginCompressionPacket
import org.geysermc.mcprotocollib.protocol.packet.login.clientbound.ClientboundLoginFinishedPacket
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundKeyPacket
import org.kvxd.blockgameproxy.BlockGameProxy
import org.kvxd.blockgameproxy.core.createEncryption
import org.kvxd.blockgameproxy.core.handler.IncomingPacketHandler
import org.kvxd.blockgameproxy.core.setCompressionThreshold
import java.util.*

class SKeyHandler : IncomingPacketHandler<ServerboundKeyPacket> {

    override fun handle(session: Session, packet: ServerboundKeyPacket): ServerboundKeyPacket {
        val privateKey = BlockGameProxy.KEY_PAIR.private

        check(BlockGameProxy.CHALLENGE.contentEquals(packet.getEncryptedChallenge(privateKey))) { "Protocol error" }

        val key = packet.getSecretKey(privateKey)
        session.setEncryption(createEncryption(key))

        session.send(ClientboundLoginCompressionPacket(256))
        session.setCompressionThreshold(256)

        val profile = GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + "OGKush32").toByteArray()), "OGKush32")

        session.send(ClientboundLoginFinishedPacket(profile))

        return packet
    }

}