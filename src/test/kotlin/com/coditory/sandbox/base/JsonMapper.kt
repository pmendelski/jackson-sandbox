package com.coditory.sandbox.base

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

object JsonMapper {
    private val objectMapper =
        ObjectMapper().apply {
            registerModule(JavaTimeModule())
            registerModule(Jdk8Module())
            registerModule(KotlinModule())
            enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING)
            disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES)
            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        }

    fun toJson(obj: Any): String {
        return objectMapper.writer()
            .withDefaultPrettyPrinter()
            .writeValueAsString(obj)
    }

    fun <T> parseJson(json: String, expectedType: Class<T>): T {
        return objectMapper.readValue(json, expectedType)
    }

    fun <T> parseJsonList(json: String, expectedType: Class<T>): T {
        val javaType = objectMapper.typeFactory
            .constructCollectionType(List::class.java, expectedType)
        return objectMapper.readValue(json, javaType)
    }
}
