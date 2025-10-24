package org.kvxd.blockgameproxy.core.cache.caches

import java.util.*

class PlayerCache(
    var username: String = "Steve",
    var uuid: UUID  = offlineUUID(username),
    var heldSlot: Int = 0,
    var invincible: Boolean = false,
    var canFly: Boolean = false,
    var flying: Boolean = false,
    var creative: Boolean = false,
    var flySpeed: Float = 0f,
    var walkSpeed: Float = 0f
)

fun offlineUUID(username: String): UUID =
    UUID.nameUUIDFromBytes(("OfflinePlayer:$username").toByteArray())