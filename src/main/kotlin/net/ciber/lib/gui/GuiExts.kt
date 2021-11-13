package net.ciber.lib.gui

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.serializer
import net.ciber.lib.CiberConfig
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

@ExperimentalSerializationApi
fun GuiConfiguration.registerPlugin(plugin: Plugin) {
    guis[plugin] = mutableMapOf()
    playerOpenGui[plugin] = mutableMapOf()
}

@ExperimentalSerializationApi
fun GuiConfiguration.saveGuis(plugin: Plugin) {
    val storage = CiberConfig(plugin, "guis/gui.yml")
    if(storage.exists()) return
    storage.save()
    val guiString = Yaml.default.encodeToString(serializer(), guis[plugin])
    storage.writeText(guiString)
}

@ExperimentalSerializationApi
fun GuiConfiguration.loadGuis(plugin: Plugin) {
    val storage = CiberConfig(plugin, "guis/gui.yml")
    if(!storage.exists()) return
    val guiString = storage.readText()
    if (guiString.isEmpty()) return
    val guiMap: MutableMap<String, CiberGui> = Yaml.default.decodeFromString(guiString)
    guis[plugin] = guiMap
}

@ExperimentalSerializationApi
fun GuiConfiguration.exists(plugin: Plugin): Boolean {
    val storage = CiberConfig(plugin, "guis/gui.yml")
    return storage.exists()
}

@ExperimentalSerializationApi
fun GuiConfiguration.registerGui(plugin: Plugin, guiName: String, gui: CiberGui) {
    guis[plugin]!![guiName] = gui
}

@ExperimentalSerializationApi
fun GuiConfiguration.registerGuis(plugin: Plugin, guiMap: MutableMap<String, CiberGui>) {
    guis[plugin]!!.putAll(guiMap)
}

@ExperimentalSerializationApi
fun GuiConfiguration.removeGui(plugin: Plugin, name: String) {
    guis[plugin]!!.remove(name)
}

@ExperimentalSerializationApi
fun GuiConfiguration.openGui(plugin: Plugin, name: String, player: Player) {
    val gui = getGui(plugin, name) ?: return
    val inventory = cloneGui(plugin, gui)
    playerOpenGui[plugin]!![player] = inventory
    player.openInventory(inventory)
}

@ExperimentalSerializationApi
fun GuiConfiguration.changeAndOpenGui(
    plugin: Plugin,
    name: String,
    player: Player,
    title: String,
    buttons: Map<Int, Button>
) {
    val gui = getGui(plugin, name) ?: return
    val inventory = cloneGuiAndChangeTitle(plugin, gui, title)
    buttons.forEach { (slot, button) ->
        inventory.setItem(slot, button.item)
    }
    playerOpenGui[plugin]!![player] = inventory
    player.openInventory(inventory)
}

@ExperimentalSerializationApi
fun GuiConfiguration.cloneGuiAndChangeTitle(plugin: Plugin, gui: CiberGui, newTitle: String): Inventory {
    val inventory = Bukkit.createInventory(null, gui.lines*9, newTitle)
    gui.buttons.forEach{ (slot, guiButton) ->
        inventory.setItem(slot, guiButton.item)
    }
    return inventory
}

@ExperimentalSerializationApi
fun GuiConfiguration.cloneGui(plugin: Plugin, gui: CiberGui): Inventory {
    val inventory = Bukkit.createInventory(null, gui.lines*9, gui.title)
    gui.buttons.forEach{ (slot, guiButton) ->
        inventory.setItem(slot, guiButton.item)
    }
    return inventory
}

@ExperimentalSerializationApi
fun GuiConfiguration.getGui(plugin: Plugin, name: String): CiberGui? {
    return guis[plugin]!![name]
}

@ExperimentalSerializationApi
fun GuiConfiguration.getGuiByTitle(plugin: Plugin, title: String): CiberGui? {
    guis[plugin]!!.forEach{ (_, gui) ->
        if(gui.title == title) return gui
    }
    return null
}

@ExperimentalSerializationApi
fun GuiConfiguration.getPlayerOpenGui(plugin: Plugin, player: Player): Inventory? {
    return playerOpenGui[plugin]!![player]
}