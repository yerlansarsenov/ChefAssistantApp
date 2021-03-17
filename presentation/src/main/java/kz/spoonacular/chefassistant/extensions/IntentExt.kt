package kz.spoonacular.chefassistant.extensions

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable


fun Intent.putExtraOfAny(key: String, value: Any?) {
    when (value) {
        is Boolean -> putExtra(key, value)
        is Byte -> putExtra(key, value)
        is Char -> putExtra(key, value)
        is Double -> putExtra(key, value)
        is Float -> putExtra(key, value)
        is Int -> putExtra(key, value)
        is Long -> putExtra(key, value)
        is Short -> putExtra(key, value)
        is Bundle -> putExtra(key, value)
        is CharSequence -> putExtra(key, value)
        is Parcelable -> putExtra(key, value)
        is BooleanArray -> putExtra(key, value)
        is ByteArray -> putExtra(key, value)
        is CharArray -> putExtra(key, value)
        is DoubleArray -> putExtra(key, value)
        is FloatArray -> putExtra(key, value)
        is IntArray -> putExtra(key, value)
        is LongArray -> putExtra(key, value)
        is ShortArray -> putExtra(key, value)
        is Serializable -> putExtra(key, value)
    }
}