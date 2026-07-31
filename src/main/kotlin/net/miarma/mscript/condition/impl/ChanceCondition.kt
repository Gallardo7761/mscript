package net.miarma.mscript.condition.impl

import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext

class ChanceCondition(private val chance: Int) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return (Math.random() * 100) < chance
    }
}
