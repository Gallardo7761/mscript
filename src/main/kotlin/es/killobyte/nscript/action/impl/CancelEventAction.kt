package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.event.Cancellable

class CancelEventAction : Action {
    override fun execute(context: ScriptContext) {
        (context.event as? Cancellable)?.isCancelled = true
    }
}
