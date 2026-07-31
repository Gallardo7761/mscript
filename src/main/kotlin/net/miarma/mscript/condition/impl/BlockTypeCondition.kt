package net.miarma.mscript.condition.impl

import net.miarma.mscript.condition.Condition
import net.miarma.mscript.context.ScriptContext
import org.bukkit.Material

class BlockTypeCondition(private val material: Material) : Condition {
    override fun test(context: ScriptContext): Boolean {
        return context.block?.type == material
    }
}
