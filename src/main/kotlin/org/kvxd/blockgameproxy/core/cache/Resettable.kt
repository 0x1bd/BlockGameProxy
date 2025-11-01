package org.kvxd.blockgameproxy.core.cache

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Resettable<T>(private val initialValue: T): ReadWriteProperty<Any?, T> {

    private var value: T = initialValue
    private var isSet = true

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (!isSet)
            throw IllegalStateException("${property.name} has not beed set")

        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        this.isSet = true
    }

    fun reset() {
        this.value = initialValue
        this.isSet = false
    }

}

class ResettableWithDefault<T>(private val initialValue: T) : ReadWriteProperty<Any?, T> {
    private var value: T = initialValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = value

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun reset() {
        this.value = initialValue
    }
}