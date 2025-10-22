package org.kvxd.blockgameproxy

import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries
import org.slf4j.LoggerFactory

object BlockGameProxy {

    val LOGGER = LoggerFactory.getLogger("Main")

    fun initialize() {
        PacketHandlerRegistries.initialize()

        ProxyClient.initialize()
        ProxyClient.connect()

        Thread.currentThread().join()
    }

}