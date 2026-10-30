package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext

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
