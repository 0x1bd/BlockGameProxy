package org.kvxd.blockgameproxy.core.handler

import org.geysermc.mcprotocollib.network.packet.Packet
import kotlin.reflect.KClass

class PacketHandlerRegistry {

    @PublishedApi
    internal val incoming = mutableMapOf<KClass<out Packet>, IncomingPacketHandler<out Packet>>()

    @PublishedApi
    internal val outgoing = mutableMapOf<KClass<out Packet>, OutgoingPacketHandler<out Packet>>()

    @PublishedApi
    internal val postOutgoing = mutableMapOf<KClass<out Packet>, PostOutgoingPacketHandler<out Packet>>()

    inline fun <reified T : Packet> registerIncoming(handler: IncomingPacketHandler<T>) {
        incoming[T::class] = handler
    }

    inline fun <reified T : Packet> registerOutgoing(handler: OutgoingPacketHandler<T>) {
        outgoing[T::class] = handler
    }

    inline fun <reified T : Packet> registerPostOutgoing(handler: PostOutgoingPacketHandler<T>) {
        postOutgoing[T::class] = handler
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> getIncoming(packetClass: KClass<out T>): IncomingPacketHandler<T>? {
        return incoming[packetClass] as? IncomingPacketHandler<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> getOutgoing(packetClass: KClass<out T>): OutgoingPacketHandler<T>? {
        return outgoing[packetClass] as? OutgoingPacketHandler<T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Packet> getPostOutgoing(packetClass: KClass<out T>): PostOutgoingPacketHandler<T>? {
        return postOutgoing[packetClass] as? PostOutgoingPacketHandler<T>
    }
}