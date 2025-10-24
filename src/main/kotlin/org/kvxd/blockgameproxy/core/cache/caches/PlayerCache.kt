package org.kvxd.blockgameproxy.core.cache.caches

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PositionElement
import java.util.*

class PlayerCache(
    var username: String = "Steve",
    var uuid: UUID = offlineUUID(username),
    var heldSlot: Int = 0,
    val abilities: Abilities = Abilities(),
    val position: Position = Position(),
    var health: Float = 20f,
    var food: Int = 20,
    var saturation: Float = 5f,
    var exp: Float = 0f,
    var expLevel: Int = 0,
    var totalExp: Int = 0
)

data class Abilities(
    var invincible: Boolean = false,
    var canFly: Boolean = false,
    var flying: Boolean = false,
    var creative: Boolean = false,
    var flySpeed: Float = 0f,
    var walkSpeed: Float = 0f
)

data class Position(
    var position: Vector3d = Vector3d.from(0.0),
    var delta: Vector3d = Vector3d.from(0.0),
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var flags: List<PositionElement> = emptyList()
)

fun offlineUUID(username: String): UUID =
    UUID.nameUUIDFromBytes(("OfflinePlayer:$username").toByteArray())