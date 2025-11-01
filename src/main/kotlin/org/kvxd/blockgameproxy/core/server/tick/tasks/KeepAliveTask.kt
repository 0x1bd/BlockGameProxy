package org.kvxd.blockgameproxy.core.server.tick.tasks

import org.geysermc.mcprotocollib.protocol.packet.common.clientbound.ClientboundKeepAlivePacket
import org.kvxd.blockgameproxy.core.server.ProxyServer
import org.kvxd.blockgameproxy.core.server.tick.TickTask

class KeepAliveTask : TickTask {

    companion object {

        // 15 seconds in ticks
        const val TICKS = 15 * 20
    }

    override fun tick(currentTick: Int) {
        if (currentTick % TICKS == 0) {
            ProxyServer.currentSession?.send(
                ClientboundKeepAlivePacket(System.currentTimeMillis())
            )
        }
    }

}