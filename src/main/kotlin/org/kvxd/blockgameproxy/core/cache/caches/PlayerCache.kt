package org.kvxd.blockgameproxy.core.cache.caches

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PositionElement
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.ResetCondition

object PlayerCache : Cache() {

    var isInvincible = false
    var canFly = false
    var flying = false
    var inCreative = false
    var flySpeed = 0f
    var walkSpeed = 0f

    var position = Vector3d.ZERO
    var positionDelta = Vector3d.ZERO
    var yaw = 0f
    var pitch = 0f
    var positionFlags = emptyList<PositionElement>()

    override fun reset() {
        isInvincible = false
        canFly = false
        flying = false
        inCreative = false
        flySpeed = 0f
        walkSpeed = 0f

        position = Vector3d.ZERO
        positionDelta = Vector3d.ZERO
        yaw = 0f
        pitch = 0f
        positionFlags = emptyList()
    }

    override val resetCondition: ResetCondition = ResetCondition.Disconnect

}