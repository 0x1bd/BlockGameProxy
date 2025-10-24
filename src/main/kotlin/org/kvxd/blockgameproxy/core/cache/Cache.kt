package org.kvxd.blockgameproxy.core.cache

import org.kvxd.blockgameproxy.core.cache.caches.*

object Cache {

    val COMMAND = CommandCache()
    val PLAYER = PlayerCache()
    val LOGIN = LoginCache()
    val REGISTRY = RegistryCache()
    val WORLD = WorldCache()

}