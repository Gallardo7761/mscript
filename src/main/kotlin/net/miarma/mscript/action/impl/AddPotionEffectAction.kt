package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class AddPotionEffectAction(
    private val type: PotionEffectType,
    private val durationTicks: Int,
    private val amplifier: Int
) : Action {
    override fun execute(context: ScriptContext) {
        val effect = PotionEffect(type, durationTicks, amplifier)
        (context.targetEntity as? LivingEntity)?.addPotionEffect(effect)
            ?: context.player?.addPotionEffect(effect)
    }
}
