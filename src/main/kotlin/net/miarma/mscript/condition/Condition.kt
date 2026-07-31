package net.miarma.mscript.condition

import net.miarma.mscript.context.ScriptContext

/**
 * Represents a condition that must be met for an action to execute.
 */
fun interface Condition {
    fun test(context: ScriptContext): Boolean
}
