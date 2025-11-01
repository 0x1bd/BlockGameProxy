package org.kvxd.blockgameproxy.core.cache

//TODO
sealed class ResetCondition {

    object Never: ResetCondition()

    object Disconnect: ResetCondition()

}