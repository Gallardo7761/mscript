package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.event.entity.PotionSplashEvent

class ForEachAffectedEntityAction(private val actions: List<Action>) : Action {
    override fun execute(context: ScriptContext) {
        (context.event as? PotionSplashEvent)?.affectedEntities?.let { affectedEntities ->
            val originalTarget = context.targetEntity
            affectedEntities.forEach { entity ->
                context.targetEntity = entity
                actions.forEach { action ->
                    action.execute(context)
                }
            }
            context.targetEntity = originalTarget
        }
    }
}
