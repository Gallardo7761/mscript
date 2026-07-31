package net.miarma.mscript.action.impl

import net.miarma.mscript.action.Action
import net.miarma.mscript.context.ScriptContext
import org.bukkit.inventory.meta.Damageable

class DamageItemAction(private val damage: Int) : Action {
    override fun execute(context: ScriptContext) {
        context.player?.inventory?.itemInMainHand?.let { item ->
            (item.itemMeta as? Damageable)?.let {
                it.damage += damage
                item.itemMeta = it
            }
        }
    }
}
