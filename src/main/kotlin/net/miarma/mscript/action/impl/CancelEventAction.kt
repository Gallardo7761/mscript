package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.event.Cancellable

class CancelEventAction : Action {
    override fun execute(context: ScriptContext) {
        (context.event as? Cancellable)?.isCancelled = true
    }
}
