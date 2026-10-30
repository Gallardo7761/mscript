package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext

class ClearHandAction : Action {
    override fun execute(context: ScriptContext) {
        context.player?.inventory?.setItemInMainHand(null)
    }
}
