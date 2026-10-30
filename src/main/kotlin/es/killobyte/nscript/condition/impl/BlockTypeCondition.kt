package es.killobyte.nscript.condition.impl

import es.killobyte.nscript.condition.Condition
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Material

class BlockTypeCondition(private val material: Material) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.block?.type == material
    }
}
