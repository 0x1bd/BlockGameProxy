package org.kvxd.blockgameproxy.core.cache.caches

import org.cloudburstmc.math.vector.Vector3d
import org.geysermc.mcprotocollib.protocol.data.game.entity.player.PositionElement
import org.kvxd.blockgameproxy.core.cache.Cache
import org.kvxd.blockgameproxy.core.cache.ResetCondition

object PlayerCache : Cache() {

    var isInvincible by resettableWithDefault(false)
    var canFly by resettableWithDefault(false)
    var flying by resettableWithDefault(false)
    var inCreative by resettableWithDefault(false)
    var flySpeed by resettableWithDefault(0f)
    var walkSpeed by resettableWithDefault(0f)

    var position by resettableWithDefault(Vector3d.ZERO)
    var positionDelta by resettableWithDefault(Vector3d.ZERO)
    var yaw by resettableWithDefault(0f)
    var pitch by resettableWithDefault(0f)
    var positionFlags by resettableWithDefault(emptyList<PositionElement>())

    override val resetCondition: ResetCondition = ResetCondition.Disconnect

}