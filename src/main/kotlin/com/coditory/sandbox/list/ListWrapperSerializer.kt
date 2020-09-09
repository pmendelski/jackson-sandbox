package com.coditory.sandbox.list

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider

class ListWrapperSerializer<T: Any> : JsonSerializer<ListWrapper<T>>() {
    private val supportedTypes = ListWrapperDeserializer.supportedTypes.entries
        .map { it.value to it.key }
        .toMap()

    override fun serialize(value: ListWrapper<T>?, generator: JsonGenerator, serializers: SerializerProvider) {
        if (value == null) {
            generator.writeNull()
            return
        }
        if (value.items.isEmpty()) {
            generator.writeStartObject()
            generator.writeStringField("type", "Object")
            generator.writeArrayFieldStart("items")
            generator.writeEndArray()
            generator.writeEndObject()
        }
        generator.writeStartObject()
        generator.writeStringField("type", supportedTypes[value.itemsType])
        generator.writeArrayFieldStart("items")
        value.items.forEach {
            serializers.defaultSerializeValue(it, generator)
        }
        generator.writeEndArray()
        generator.writeEndObject()
    }


}
