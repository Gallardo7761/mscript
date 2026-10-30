package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.entity.Ageable

class SetBabyAction : Action {
    override fun execute(context: ScriptContext) {
        (context.targetEntity as? Ageable)?.setBaby()
    }
}
