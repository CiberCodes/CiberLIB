package net.ciber.lib

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@ExperimentalSerializationApi
@Serializer(String::class)
object TextFormatAdapter: KSerializer<String> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value.replace("§", "&"))
    }

    override fun deserialize(decoder: Decoder): String {
        return decoder.decodeString().replace("&", "§")
    }
}