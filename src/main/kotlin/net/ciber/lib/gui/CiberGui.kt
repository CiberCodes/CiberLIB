package net.ciber.lib.gui

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import net.ciber.lib.TextFormatAdapter
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.plugin.Plugin

@ExperimentalSerializationApi
@Serializable
data class CiberGui(
    val lines: Int,
    @Serializable(TextFormatAdapter::class) val title: String,
    val isLockable: Boolean,
    val buttons: Map<Int, Button>
)

@ExperimentalSerializationApi
@Serializable
data class GuiConfiguration(
    val guis: MutableMap<Plugin, MutableMap<String, CiberGui>>,
    val playerOpenGui: MutableMap<Plugin, MutableMap<Player, Inventory>>
)