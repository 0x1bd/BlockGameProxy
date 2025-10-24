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

        CLIENT.registerIncoming(CFinishConfigurationHandler())
        CLIENT.registerIncoming(CKeepAliveHandler())
        CLIENT.registerIncoming(CLoginCompressionHandler())
        CLIENT.registerIncoming(CLoginFinishedHandler())
        CLIENT.registerIncoming(CCLoginHandler())
        CLIENT.registerIncoming(CPlayerAbilitiesHandler())
        CLIENT.registerIncoming(CRegistryDataHandler())
        CLIENT.registerIncoming(CSelectKnownPacks())
        CLIENT.registerIncoming(CSetHeldSlotHandler())
        CLIENT.registerIncoming(CUpdateFeaturesHandler())
        CLIENT.registerIncoming(CUpdateRecipesHandler())
        CLIENT.registerIncoming(CUpdateTagsHandler())

        SERVER.registerIncoming(SFinishConfigurationHandler())
        SERVER.registerIncoming(SHelloHandler())
        SERVER.registerIncoming(SIntentHandler())
        SERVER.registerIncoming(SKeyHandler())
        SERVER.registerIncoming(SLoginAckHandler())
        SERVER.registerIncoming(SPingHandler())
        SERVER.registerIncoming(SStatusHandler())

        //TODO: Add remaining handlers (position, light data, chunks etc.)
        //TODO: Generalize shared data. (NO SharedData singleton, instead BlockData, PlayerData, LoginData and so on.)
    }

}