package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.entity.LivingEntity

class KillEntityAction : Action {
    override fun execute(context: ScriptContext) {
        (context.targetEntity as? LivingEntity)?.health = 0.0
    }
}
