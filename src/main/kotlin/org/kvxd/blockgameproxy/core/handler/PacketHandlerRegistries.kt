package org.kvxd.blockgameproxy.core.handler

import org.kvxd.blockgameproxy.core.client.handlers.incoming.CBlockUpdateHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CCLoginHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CChunkHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CCommandsHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CEntityHandlers.registerEntityHandlers
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CFinishConfigurationHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CKeepAliveHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CLightHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CLoginCompressionHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CLoginFinishedHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CPlayerAbilitiesHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CPlayerInfoHandlers.registerTabListHandlers
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CPlayerPosHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CRegistryDataHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CSectionBlocksUpdateHandler
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CSelectKnownPacks
import org.kvxd.blockgameproxy.core.client.handlers.incoming.CUpdateTagsHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SFinishConfigurationHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SHelloHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SIntentHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SKeepAliveHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SKeyHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SLoginAckHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SMovePlayerPosHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SMovePlayerPosRotHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SPingHandler
import org.kvxd.blockgameproxy.core.server.handlers.incoming.SStatusHandler
import org.kvxd.blockgameproxy.core.server.handlers.outgoing.SLoginFinishedHandler
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
        SERVER.registerServerOutgoing()
    }

    private fun PacketHandlerRegistry.registerClientIncoming() {
        registerIncoming(CBlockUpdateHandler())
        registerIncoming(CChunkHandler())
        registerIncoming(CCLoginHandler())
        registerIncoming(CCommandsHandler())
        registerEntityHandlers()
        registerIncoming(CFinishConfigurationHandler())
        registerIncoming(CKeepAliveHandler())
        registerIncoming(CLightHandler())
        registerIncoming(CLoginCompressionHandler())
        registerIncoming(CLoginFinishedHandler())
        registerIncoming(CPlayerAbilitiesHandler())
        registerTabListHandlers()
        registerIncoming(CPlayerPosHandler())
        registerIncoming(CRegistryDataHandler())
        registerIncoming(CSectionBlocksUpdateHandler())
        registerIncoming(CSelectKnownPacks())
        registerIncoming(CUpdateTagsHandler())
    }

    private fun PacketHandlerRegistry.registerClientOutgoing() {

    }

    private fun PacketHandlerRegistry.registerServerIncoming() {
        registerIncoming(SFinishConfigurationHandler())
        registerIncoming(SHelloHandler())
        registerIncoming(SIntentHandler())
        registerIncoming(SKeepAliveHandler())
        registerIncoming(SKeyHandler())
        registerIncoming(SLoginAckHandler())
        registerIncoming(SMovePlayerPosHandler())
        registerIncoming(SMovePlayerPosRotHandler())
        registerIncoming(SPingHandler())
        registerIncoming(SStatusHandler())
    }

    private fun PacketHandlerRegistry.registerServerOutgoing() {
        registerOutgoing(SLoginFinishedHandler())
    }
}
