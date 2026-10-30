package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Sound

class PlaySoundAction(
    private val sound: Sound,
    private val volume: Float,
    private val pitch: Float
) : Action {
    override fun execute(context: ScriptContext) {
        context.player?.playSound(context.player!!.location, sound, volume, pitch)
    }
}
