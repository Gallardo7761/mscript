package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext

class ClearHandAction : Action {
    override fun execute(context: ScriptContext) {
        context.player?.inventory?.setItemInMainHand(null)
    }
}
