package net.miarma.mscript

import net.miarma.mscript.action.Action
import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext

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
