package net.miarma.mscript.condition.impl

import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext

class AlwaysCondition : Condition {
    override fun test(context: ScriptContext): Boolean {
        return true
    }
}
