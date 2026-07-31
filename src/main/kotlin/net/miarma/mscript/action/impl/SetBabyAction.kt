package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.entity.Ageable

class SetBabyAction : Action {
    override fun execute(context: ScriptContext) {
        (context.targetEntity as? Ageable)?.setBaby()
    }
}
