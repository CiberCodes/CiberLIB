package net.ciber.lib

import kotlinx.serialization.ExperimentalSerializationApi
import net.ciber.lib.gui.Button
import net.ciber.lib.gui.ButtonImpl
import net.ciber.lib.gui.getGuiByTitle
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

fun InventoryCloseEvent.cancel(plugin: Plugin) {
    var counter = 0
    object : BukkitRunnable() {
        override fun run() {
            if (counter == 1) {
                player.openInventory(inventory)
                cancel()
            }
            counter++
        }
    }.runTaskTimer(plugin, 1, 1)
}

@ExperimentalSerializationApi
fun InventoryClickEvent.cancel(plugin: Plugin) {
    try {
        val instance = JavaPlugin.getPlugin(CiberLIB::class.java)
        val player = whoClicked as Player
        instance.guiConfiguration.getGuiByTitle(plugin, player.openInventory.title) ?: return
        isCancelled = true
    }catch (e: Exception) {
        e.printStackTrace()
    }
}

@ExperimentalSerializationApi
fun InventoryClickEvent.buttonExecute(
    plugin: Plugin,
    newTitle: String? = null,
    newButtons: Map<Int, Button>? = null
) {
    try {
        val instance = JavaPlugin.getPlugin(CiberLIB::class.java)
        val gui = instance.guiConfiguration.getGuiByTitle(plugin, clickedInventory.title) ?: return
        val button = gui.buttons[rawSlot] ?: return
        val function = instance.functionsMap[button.function] ?: return
        if (function is ButtonImpl) function.onClick(plugin, whoClicked as Player, button, newTitle, newButtons)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}