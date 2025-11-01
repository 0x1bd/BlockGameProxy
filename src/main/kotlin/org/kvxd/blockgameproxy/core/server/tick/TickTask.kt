package org.kvxd.blockgameproxy.core.server.tick

interface TickTask {
    fun tick(currentTick: Int)
}