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
        CLIENT.registerIncoming(CLoginHandler())
        CLIENT.registerIncoming(CPlayerAbilitiesHandler())
        CLIENT.registerIncoming(CRegistryDataHandler())
        CLIENT.registerIncoming(CSelectKnownPacks())
        CLIENT.registerIncoming(CSetHeldSlotHandler())
        CLIENT.registerIncoming(CUpdateFeaturesHandler())
        CLIENT.registerIncoming(CUpdateRecipesHandler())
        CLIENT.registerIncoming(CUpdateTagsHandler())

        //TODO: Add remaining handlers (position, light data, chunks etc.)
        //TODO: Generalize shared data. (NO SharedData singleton, instead BlockData, PlayerData, LoginData and so on.)
    }

}