package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext

class ActionIfChanceAction(
    private val chance: Int,
    private val successActions: List<Action>,
    private val failActions: List<Action>
) : Action {
    override fun execute(context: ScriptContext) {
        if ((Math.random() * 100) < chance) {
            successActions.forEach { it.execute(context) }
        } else {
            failActions.forEach { it.execute(context) }
        }
    }
}
