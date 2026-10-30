package es.killobyte.nscript.action

import es.killobyte.nscript.context.ScriptContext

/**
 * Represents an action that can be executed.
 */
fun interface Action {
    fun execute(context: ScriptContext)
}
