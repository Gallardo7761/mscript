package net.miarma.mscript.action

import net.miarma.mscript.context.ScriptContext

/**
 * Represents an action that can be executed.
 */
fun interface Action {
    fun execute(context: ScriptContext)
}
