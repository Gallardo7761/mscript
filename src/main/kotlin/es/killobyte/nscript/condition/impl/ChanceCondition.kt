package es.killobyte.nscript.condition.impl

import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext

class ChanceCondition(private val chance: Int) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return (Math.random() * 100) < chance
    }
}
