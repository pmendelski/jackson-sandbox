package com.coditory.sandbox.list

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class ListWrapperDeserializer<T: Any> : JsonDeserializer<ListWrapper<T>>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ListWrapper<T> {
        val node = parser.readValueAsTree<JsonNode>()
        val type = node.get("type").asText()
        val resolvedType: Class<out Any> = supportedTypes[type]!!
        val jacksonType = context.typeFactory.constructType(resolvedType)
        val deserializer = context.findRootValueDeserializer(jacksonType)
        val items = node.get("items").map {
            val itemParser = it.traverse(parser.codec)
            itemParser.nextToken()
            deserializer.deserialize(itemParser, context)
        }.map { resolvedType.cast(it) }
        return ListWrapper(resolvedType as Class<T>, items as List<T>)
    }

    companion object {
        val supportedTypes: Map<String, Class<out Any>> = mapOf(
            "Integer" to Integer::class.java,
            "Value" to Value::class.java,
            "IfaceValue" to IfaceValue::class.java,
            "IfaceValueA" to IfaceValueA::class.java,
            "IfaceValueB" to IfaceValueB::class.java
        )
    }
}
