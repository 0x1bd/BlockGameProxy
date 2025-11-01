package org.kvxd.blockgameproxy.core.handler

import org.kvxd.blockgameproxy.core.client.handlers.incoming.*
import org.kvxd.blockgameproxy.core.server.handlers.incoming.*
import org.slf4j.LoggerFactory

object PacketHandlerRegistries {

    private val LOGGER = LoggerFactory.getLogger("PacketHandlers")

    val CLIENT = PacketHandlerRegistry()
    val SERVER = PacketHandlerRegistry()

    fun initialize() {
        LOGGER.debug("Registering packet handlers")

        CLIENT.registerClientIncoming()
        CLIENT.registerClientOutgoing()
        SERVER.registerServerIncoming()
    }

    private fun PacketHandlerRegistry.registerClientIncoming() {
        registerIncoming(CChunkHandler())
        registerIncoming(CCLoginHandler())
        registerIncoming(CFinishConfigurationHandler())
        registerIncoming(CLightHandler())
        registerIncoming(CLoginCompressionHandler())
        registerIncoming(CLoginFinishedHandler())
        registerIncoming(CPlayerAbilitiesHandler())
        registerIncoming(CPlayerPosHandler())
        registerIncoming(CRegistryDataHandler())
        registerIncoming(CSelectKnownPacks())
        registerIncoming(CUpdateTagsHandler())
    }

    private fun PacketHandlerRegistry.registerClientOutgoing() {

    }

    private fun PacketHandlerRegistry.registerServerIncoming() {
        registerIncoming(SFinishConfigurationHandler())
        registerIncoming(SHelloHandler())
        registerIncoming(SIntentHandler())
        registerIncoming(SKeyHandler())
        registerIncoming(SLoginAckHandler())
        registerIncoming(SPingHandler())
        registerIncoming(SStatusHandler())
    }
}
