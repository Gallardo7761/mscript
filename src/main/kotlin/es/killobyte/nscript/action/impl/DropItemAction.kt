package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class DropItemAction(
    private val material: Material,
    private val amount: Int
) : Action {
    override fun execute(context: ScriptContext) {
        val location = context.targetEntity?.location
            ?: context.block?.location
            ?: context.player?.location
            ?: context.entity?.location

        location?.world?.dropItemNaturally(location, ItemStack(material, amount))
    }
}
