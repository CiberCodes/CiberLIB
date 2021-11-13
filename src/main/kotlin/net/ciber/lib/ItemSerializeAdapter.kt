package net.ciber.lib

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

@ExperimentalSerializationApi
@Serializer(ItemStack::class)
object ItemSerializeAdapter : KSerializer<ItemStack> {

    @Serializable
    private data class ItemStackSurrogate(
        var type: String,
        var amount: Int,
        var damage: Short,
        var meta: FixedMeta
    )

    @Serializable
    private data class FixedMeta(
        var displayName: String = "",
        var lore: List<String> = listOf(),
        var enchantments: Map<String, Int> = mapOf()
    )

    override val descriptor: SerialDescriptor = ItemStackSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ItemStack) {
        val surrogate = ItemStackSurrogate(
            value.type.toString(),
            value.amount,
            value.durability,
            FixedMeta().apply {
                if (value.itemMeta != null) {
                    displayName = value.itemMeta!!.displayName.replace("§", "&")

                    if (value.itemMeta!!.lore != null) {
                        val newLore = mutableListOf<String>()
                        value.itemMeta!!.lore?.forEach { string ->
                            newLore.add(string.replace("§", "&"))
                        }
                        lore = newLore
                    }

                    val newEnchantments = mutableMapOf<String, Int>()
                    value.itemMeta!!.enchants.forEach { (enchant, level) ->
                        newEnchantments[enchant.name.toString()] = level
                    }
                    enchantments = newEnchantments
                }
            }
        )

        encoder.encodeSerializableValue(ItemStackSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): ItemStack {
        val surrogate = decoder.decodeSerializableValue(ItemStackSurrogate.serializer())
        return ItemStack(Material.valueOf(surrogate.type), surrogate.amount, surrogate.damage).apply {
            itemMeta = itemMeta?.apply {
                displayName = surrogate.meta.displayName.replace("&", "§")
                val newLore = mutableListOf<String>()
                surrogate.meta.lore.forEach { string ->
                    newLore.add(string.replace("&", "§"))
                }
                lore = newLore
                surrogate.meta.enchantments.forEach{ (ench, level) ->
                    addUnsafeEnchantment(Enchantment.getByName(ench), level)
                }
            }
        }
    }
}