package ru.tjournal.util

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class PreferenceProperty<T>(
    private val key: String,
    private val defaultValue: T,
    private val sharedPreferences: SharedPreferences,
    private val getter: SharedPreferences.(String, T) -> T,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadWriteProperty<Any, T> {
    private var value = getInitialValue()

    override fun getValue(thisRef: Any, property: KProperty<*>): T = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
        sharedPreferences
            .edit()
            .setter(key, value)
            .apply()
    }

    private fun getInitialValue(): T = sharedPreferences.getter(key, defaultValue)
}

fun SharedPreferences.string(
    key: String,
    defaultValue: String = ""
): ReadWriteProperty<Any, String> =
    PreferenceProperty(
        sharedPreferences = this,
        key = key,
        defaultValue = defaultValue,
        getter = SharedPreferences::getString,
        setter = SharedPreferences.Editor::putString
    ) as ReadWriteProperty<Any, String>