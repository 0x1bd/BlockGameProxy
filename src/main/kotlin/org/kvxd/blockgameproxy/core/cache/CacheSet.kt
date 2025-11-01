package org.kvxd.blockgameproxy.core.cache

import org.kvxd.blockgameproxy.core.cache.caches.LoginCache
import org.kvxd.blockgameproxy.core.cache.caches.PlayerCache
import org.kvxd.blockgameproxy.core.cache.caches.RegistryCache
import org.kvxd.blockgameproxy.core.cache.caches.chunk.ChunkCache

object CacheSet {

    val Registry = RegistryCache
    val Login = LoginCache
    val Player = PlayerCache
    val Chunk = ChunkCache

    val all = listOf(
        Registry,
        Login,
        Player,
        Chunk
    )

}