package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Material

class BreakBlockAction : Action {
    override fun execute(context: ScriptContext) {
        context.block?.type = Material.AIR
    }
}
