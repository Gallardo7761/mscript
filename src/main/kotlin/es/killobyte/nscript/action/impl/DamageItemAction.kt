package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
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
