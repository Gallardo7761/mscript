package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
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
