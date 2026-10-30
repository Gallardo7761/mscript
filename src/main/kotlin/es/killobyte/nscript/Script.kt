package es.killobyte.nscript

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext

/**
 * Represents a parsed YAML script.
 */
class Script(
    val id: String,
    private val conditions: List<Condition>,
    private val actions: List<Action>
) {
    fun execute(context: ScriptContext) {
        if (conditions.all { it.test(context) }) {
            actions.forEach { it.execute(context) }
        }
    }
}
