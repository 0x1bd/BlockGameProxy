package org.kvxd.blockgameproxy.core.server

import org.geysermc.mcprotocollib.network.event.server.ServerAdapter
import org.geysermc.mcprotocollib.network.event.server.ServerBoundEvent
import org.geysermc.mcprotocollib.network.event.server.ServerClosedEvent
import org.geysermc.mcprotocollib.network.event.server.SessionAddedEvent

class ProxyServerListener : ServerAdapter() {

    override fun serverBound(event: ServerBoundEvent) {
        ProxyServer.LOGGER.info("Server bound")
    }

    override fun serverClosed(event: ServerClosedEvent) {
        ProxyServer.LOGGER.info("Server closed")
    }

    override fun sessionAdded(event: SessionAddedEvent) {
        event.session.addListener(ServerSessionListener())
    }

}