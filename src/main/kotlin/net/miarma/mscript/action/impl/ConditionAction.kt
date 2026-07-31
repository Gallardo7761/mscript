package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext

class ConditionAction(
    private val condition: Condition,
    private val successActions: List<Action>,
    private val failActions: List<Action>
) : Action {
    override fun execute(context: ScriptContext) {
        if (condition.test(context)) {
            successActions.forEach { it.execute(context) }
        } else {
            failActions.forEach { it.execute(context) }
        }
    }
}
