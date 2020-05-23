package app.boletinhos.preferences

import android.content.SharedPreferences

object FakePreferences : SharedPreferences {
    private val prefs = mutableMapOf<String, Any>()

    override fun contains(key: String?): Boolean {
        return prefs.contains(key)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs[key] as? Boolean ?: defValue
    }

    override fun edit(): SharedPreferences.Editor {
       return Editor
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not implemented")
    }

    override fun getInt(key: String?, defValue: Int): Int {
        TODO("Not implemented")
    }

    override fun getAll(): MutableMap<String, *> {
        TODO("Not implemented")
    }

    override fun getLong(key: String?, defValue: Long): Long {
        TODO("Not implemented")
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        TODO("Not implemented")
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> {
        TODO("Not implemented")
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        TODO("Not implemented")
    }

    override fun getString(key: String?, defValue: String?): String? {
        TODO("Not implemented")
    }

    object Editor : SharedPreferences.Editor {
        private val temp = mutableMapOf<String, Any>()

        override fun apply() {
            prefs.putAll(temp)
        }

        override fun putBoolean(key: String, value: Boolean) = apply {
            temp[key] = value
        }

        override fun clear() = apply {
            temp.clear()
            prefs.clear()
        }

        override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
            TODO("Not implemented")
        }

        override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
            TODO("Not implemented")
        }

        override fun remove(key: String?): SharedPreferences.Editor {
            TODO("Not implemented")
        }

        override fun putStringSet(
            key: String?,
            values: MutableSet<String>?
        ): SharedPreferences.Editor {
            TODO("Not implemented")
        }

        override fun commit(): Boolean {
            TODO("Not implemented")
        }

        override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
            TODO("Not implemented")
        }

        override fun putString(key: String?, value: String?): SharedPreferences.Editor {
            TODO("Not implemented")
        }

    }
}