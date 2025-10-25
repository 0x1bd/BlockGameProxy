package org.kvxd.blockgameproxy.core.cache

import org.kvxd.blockgameproxy.core.cache.caches.*
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache

object Cache {

    val COMMAND = CommandCache()
    val PLAYER = PlayerCache()
    val LOGIN = LoginCache()
    val REGISTRY = RegistryCache()
    val CHUNK = ChunkCache()
    val WORLD = WorldCache()

}