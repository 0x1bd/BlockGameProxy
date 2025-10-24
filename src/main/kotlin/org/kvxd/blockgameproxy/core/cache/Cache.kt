package org.kvxd.blockgameproxy.core.cache

import org.kvxd.blockgameproxy.core.cache.caches.CommandCache
import org.kvxd.blockgameproxy.core.cache.caches.LoginCache
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache

object Cache {

    val COMMAND = CommandCache()
    val PLAYER = PlayerCache()
    val LOGIN = LoginCache()
    val REGISTRY = RegistryCache()

}