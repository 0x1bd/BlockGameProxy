package dev.ogkush32.kushproxy.listeners

import dev.ogkush32.kushproxy.PacketHandler
import dev.ogkush32.kushproxy.ProxyClient
import org.geysermc.mcprotocollib.network.event.server.ServerAdapter
import org.geysermc.mcprotocollib.network.event.server.ServerBoundEvent
import org.geysermc.mcprotocollib.network.event.server.SessionAddedEvent
import org.geysermc.mcprotocollib.network.event.server.SessionRemovedEvent

class ServerEventListener(
    private val remoteHost: String,
    private val remotePort: Int
) : ServerAdapter() {

    override fun serverBound(event: ServerBoundEvent) {
        println("Proxy server bound and accepting connections.")
    }

    override fun sessionAdded(event: SessionAddedEvent) {
        val playerSession = event.session
        println("Incoming player session from ${playerSession.remoteAddress}")

        val client = ProxyClient(remoteHost, remotePort)
        client.start()

        client.onPacketReceived = { packet ->
            PacketHandler.handleServerToPlayerPacket(playerSession, packet)
        }

        playerSession.addListener(PlayerSessionListener(client))
    }

    override fun sessionRemoved(event: SessionRemovedEvent) {
        println("Player session removed: ${event.session.remoteAddress}")
    }
}