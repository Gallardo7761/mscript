package es.killobyte.nscript.condition

import es.killobyte.nscript.context.ScriptContext

/**
 * Represents a condition that must be met for an action to execute.
 */
fun interface Condition {
    fun test(context: ScriptContext): Boolean
}
