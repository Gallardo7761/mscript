package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
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
