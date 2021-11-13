package net.ciber.lib.gui

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import net.ciber.lib.ItemSerializeAdapter
import org.bukkit.inventory.ItemStack

@ExperimentalSerializationApi
@Serializable
data class Button(
    val function: String,
    val gui: String,
    @Serializable(ItemSerializeAdapter::class) val item: ItemStack
)