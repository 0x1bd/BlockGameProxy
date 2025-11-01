package org.kvxd.blockgameproxy.core.client

import org.geysermc.mcprotocollib.network.event.session.DisconnectedEvent
import org.geysermc.mcprotocollib.network.event.session.SessionAdapter
import org.kvxd.blockgameproxy.config.config
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ProxyClientReconnectListener : SessionAdapter() {

    private val scheduler = Executors.newSingleThreadScheduledExecutor()

    override fun disconnected(event: DisconnectedEvent) {
        scheduleReconnect()
    }

    private fun scheduleReconnect() {
        ProxyClient.LOGGER.info("Disconnected. Scheduling reconnect in ${config.reconnectDelaySeconds} seconds...")
        scheduler.schedule(::tryReconnect, config.reconnectDelaySeconds, TimeUnit.SECONDS)
    }

    private fun tryReconnect() {
        ProxyClient.LOGGER.info("Attempting to reconnect...")
        if (ProxyClient.connect() == null) {
            ProxyClient.LOGGER.error("Reconnect failed. Retrying in ${config.reconnectDelaySeconds} seconds.")
            scheduleReconnect()
        }
    }
}
