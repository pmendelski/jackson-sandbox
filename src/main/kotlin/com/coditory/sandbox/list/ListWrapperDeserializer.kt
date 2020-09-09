package com.coditory.sandbox.list

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode

class ListWrapperDeserializer : JsonDeserializer<ListWrapper<Any>>() {
    private val supportedTypes = mapOf(
        "Integer" to Integer::class.java,
        "Value" to Value::class.java,
        "IfaceValue" to IfaceValue::class.java,
        "IfaceValueA" to IfaceValueA::class.java,
        "IfaceValueB" to IfaceValueB::class.java
    )

    override fun deserialize(parser: JsonParser, context: DeserializationContext): ListWrapper<Any> {
        val node = parser.readValueAsTree<JsonNode>()
        val type = node.get("type").asText()
        val resolvedType = supportedTypes[type]
        val jacksonType = context.typeFactory.constructType(resolvedType)
        val deserializer = context.findRootValueDeserializer(jacksonType)
        val items = node.get("items").map {
            val itemParser = it.traverse(parser.codec)
            itemParser.nextToken()
            deserializer.deserialize(itemParser, context)
        }
        return ListWrapper(items)
    }
}
