package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.event.server.ServerAdapter
import org.geysermc.mcprotocollib.network.event.server.ServerBoundEvent
import org.geysermc.mcprotocollib.network.event.server.ServerClosedEvent
import org.geysermc.mcprotocollib.network.event.server.SessionAddedEvent
import org.kvxd.blockgameproxy.core.server.ServerSessionListener.Companion.currentSession

class ProxyServerListener : ServerAdapter() {

    override fun serverBound(event: ServerBoundEvent) {
        ProxyServer.LOGGER.info("Server bound")
    }

    override fun serverClosed(event: ServerClosedEvent) {
        ProxyServer.LOGGER.info("Server closed")
    }

    override fun sessionAdded(event: SessionAddedEvent) {
        // This can't be in ServerSessionListener because the
        // connectedEvent is never fired
        // CAUTION: This may also be true in status phase
        currentSession = event.session

        event.session.addListener(ServerSessionListener())
    }

}