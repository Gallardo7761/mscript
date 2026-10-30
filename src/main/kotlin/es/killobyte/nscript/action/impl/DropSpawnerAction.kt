package es.killobyte.nscript.action.impl

import es.killobyte.nscript.action.Action
import es.killobyte.nscript.context.ScriptContext
import org.bukkit.Material
import org.bukkit.block.CreatureSpawner
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BlockStateMeta

class DropSpawnerAction : Action {
    override fun execute(context: ScriptContext) {
        (context.block?.state as? CreatureSpawner)?.let { spawner ->
            val spawnerItem = ItemStack(Material.SPAWNER)
            (spawnerItem.itemMeta as? BlockStateMeta)?.let { meta ->
                meta.blockState = spawner
                spawnerItem.itemMeta = meta
            }
            context.block?.world?.dropItemNaturally(context.block!!.location, spawnerItem)
        }
    }
}
