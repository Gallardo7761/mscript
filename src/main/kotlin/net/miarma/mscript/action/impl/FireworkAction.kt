package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework

class FireworkAction(
    private val color: Color,
    private val type: FireworkEffect.Type
) : Action {
    override fun execute(context: ScriptContext) {
        val location = context.targetEntity?.location
            ?: context.block?.location
            ?: context.player?.location

        location?.world?.let { world ->
            val fw = world.spawn(location, Firework::class.java)
            val fwMeta = fw.fireworkMeta
            fwMeta.addEffect(FireworkEffect.builder().withColor(color).with(type).build())
            fwMeta.power = 0
            fw.fireworkMeta = fwMeta
            fw.detonate()
        }
    }
}
