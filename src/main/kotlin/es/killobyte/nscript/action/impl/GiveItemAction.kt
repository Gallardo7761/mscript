package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class GiveItemAction(
    private val material: Material,
    private val amount: Int
) : Action {
    override fun execute(context: ScriptContext) {
        context.player?.inventory?.addItem(ItemStack(material, amount))
    }
}
