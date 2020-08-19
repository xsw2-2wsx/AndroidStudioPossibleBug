package com.xsw22wsx.possiblebug

import kotlin.reflect.KProperty

class CustomProperty<T>(var _value: T) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T = _value

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        _value = value
    }
}