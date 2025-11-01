package org.kvxd.blockgameproxy.core.cache

abstract class Cache {

    private val resettables = mutableListOf<() -> Unit>()

    fun <T> resettable(initialValue: T): Resettable<T> {
        return Resettable(initialValue).also { delegate ->
            resettables.add { delegate.reset() }
        }
    }

    fun <T> resettableWithDefault(initialValue: T): ResettableWithDefault<T> {
        return ResettableWithDefault(initialValue).also { delegate ->
            resettables.add { delegate.reset() }
        }
    }

    fun populate(data: (Cache) -> Unit) {
        data(this)
    }

    open fun reset() {
        resettables.forEach { it() }
    }

    open val resetCondition: ResetCondition = ResetCondition.Never

}