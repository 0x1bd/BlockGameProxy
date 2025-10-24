package org.kvxd.blockgameproxy.core.cache.caches

class PlayerCache(
    var heldSlot: Int = 0,
    var invincible: Boolean = false,
    var canFly: Boolean = false,
    var flying: Boolean = false,
    var creative: Boolean = false,
    var flySpeed: Float = 0f,
    var walkSpeed: Float = 0f
)