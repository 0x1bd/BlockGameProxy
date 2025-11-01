package org.kvxd.blockgameproxy.core.handler

import org.geysermc.mcprotocollib.network.Session
import org.geysermc.mcprotocollib.network.packet.Packet

interface PacketHandler<T : Packet> {

    fun process(session: Session, packet: T): T

    val shouldForward: Boolean
        get() = true

}

interface IncomingPacketHandler<T : Packet> : PacketHandler<T>
interface OutgoingPacketHandler<T : Packet> : PacketHandler<T>
interface PostOutgoingPacketHandler<T : Packet> : PacketHandler<T>
