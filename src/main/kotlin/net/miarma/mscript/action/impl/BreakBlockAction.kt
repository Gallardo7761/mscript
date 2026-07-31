package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.Material

class BreakBlockAction : Action {
    override fun execute(context: ScriptContext) {
        context.block?.type = Material.AIR
    }
}
