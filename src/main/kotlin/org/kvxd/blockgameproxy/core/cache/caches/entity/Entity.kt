package org.kvxd.blockgameproxy.core.cache.caches.entity

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.protocol.data.game.entity.`object`.ObjectData
import org.geysermc.mcprotocollib.protocol.data.game.entity.type.EntityType
import java.util.UUID

data class Entity(
    var id: Int,
    var uuid: UUID,
    var type: EntityType,
    var pos: Vector3d,
    var pitch: Float,
    var yaw: Float,
    var headYaw: Float,
    var data: ObjectData,
    var velocity: Vector3d,
    var onGround: Boolean = true
)
