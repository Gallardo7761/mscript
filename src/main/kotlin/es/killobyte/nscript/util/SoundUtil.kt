package es.killobyte.nscript.util

import org.bukkit.NamespacedKey
import org.bukkit.Registry
import org.bukkit.Sound

object SoundUtil {
    fun getSound(soundName: String): Sound? {
        return try {
            val fieldName = soundName.trim().uppercase()
            Sound::class.java.getField(fieldName).get(null) as Sound
        } catch (e: NoSuchFieldException) {
            try {
                val key = NamespacedKey.fromString(soundName.lowercase())
                if (key != null) {
                    Registry.SOUNDS.get(key)
                } else {
                    null
                }
            } catch (ignored: Exception) {
                null
            }
        } catch (e: IllegalAccessException) {
            null
        }
    }
}
