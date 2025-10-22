package org.kvxd.blockgameproxy.core.handler

import org.kvxd.blockgameproxy.core.client.handlers.incoming.*
import org.slf4j.LoggerFactory

object PacketHandlerRegistries {

    private val LOGGER = LoggerFactory.getLogger("PacketHandlers")

    val CLIENT = PacketHandlerRegistry()
    val SERVER = PacketHandlerRegistry()

    fun initialize() {
        LOGGER.debug("Registering packet handlers")

        CLIENT.registerIncoming(CFinishConfigurationHandler())
        CLIENT.registerIncoming(CKeepAliveHandler())
        CLIENT.registerIncoming(CLoginCompressionHandler())
        CLIENT.registerIncoming(CLoginFinishedHandler())
        CLIENT.registerIncoming(CSelectKnownPacks())
    }

}