package org.kvxd.blockgameproxy

import com.kvxd.eventbus.EventBus
import org.geysermc.mcprotocollib.network.Session
import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries
import org.kvxd.blockgameproxy.core.server.ProxyServer
import org.kvxd.blockgameproxy.events.ChangeCurrentPlayerEvent
import org.slf4j.LoggerFactory
import java.security.KeyPairGenerator
import java.util.Random

object BlockGameProxy {

    val LOGGER = LoggerFactory.getLogger("Main")

    val KEY_PAIR = run {
        val gen = KeyPairGenerator.getInstance("RSA")
        gen.initialize(1024)
        gen.genKeyPair()
    }

    val CHALLENGE = ByteArray(4)

    val EVENT_BUS = EventBus.create()

    var currentPlayer: Session? = null
        set(value) {
            field = value
            EVENT_BUS.post(ChangeCurrentPlayerEvent(currentPlayer))
        }

    fun initialize() {
        Random().nextBytes(CHALLENGE)

        PacketHandlerRegistries.initialize()

        ProxyClient.initialize()
        ProxyClient.connect()

        ProxyServer.start()

        Thread.currentThread().join()
    }

}