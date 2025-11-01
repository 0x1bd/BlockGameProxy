package org.kvxd.blockgameproxy.core.cache

abstract class Cache {

    abstract fun reset()

    open val resetCondition: ResetCondition = ResetCondition.Never

}