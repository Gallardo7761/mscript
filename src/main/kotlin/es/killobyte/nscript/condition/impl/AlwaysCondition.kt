package es.killobyte.nscript.condition.impl

import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext

class AlwaysCondition : Condition {
    override fun test(context: ScriptContext): Boolean {
        return true
    }
}
