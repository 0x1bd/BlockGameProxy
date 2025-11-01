package org.kvxd.blockgameproxy

import org.kvxd.blockgameproxy.core.client.ProxyClient
import org.kvxd.blockgameproxy.core.handler.PacketHandlerRegistries
import org.kvxd.blockgameproxy.core.server.ProxyServer
import org.slf4j.LoggerFactory
import java.security.KeyPairGenerator
import java.util.*

object BlockGameProxy {

    val LOGGER = LoggerFactory.getLogger(BlockGameProxy::class.java)

    val KEY_PAIR = run {
        val gen = KeyPairGenerator.getInstance("RSA")
        gen.initialize(1024)
        gen.genKeyPair()
    }

    val CHALLENGE = ByteArray(4)

    fun initialize() {
        Random().nextBytes(CHALLENGE)

        PacketHandlerRegistries.initialize()

        ProxyClient.initialize()
        ProxyClient.connect()

        ProxyServer.start()

        Thread.currentThread().join()
    }

}