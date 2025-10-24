package org.kvxd.blockgameproxy.core.client.handlers.outgoing

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.protocol.packet.login.serverbound.ServerboundHelloPacket
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.handler.OutgoingPacketHandler

class CHelloHandler : OutgoingPacketHandler<ServerboundHelloPacket> {

    override fun handle(session: Session, packet: ServerboundHelloPacket): ServerboundHelloPacket {
        Cache.PLAYER.username = packet.username
        Cache.PLAYER.uuid = packet.profileId

        return packet
    }

}