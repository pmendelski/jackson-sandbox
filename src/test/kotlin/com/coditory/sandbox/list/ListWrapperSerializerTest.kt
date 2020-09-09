package com.coditory.sandbox.list

import com.coditory.sandbox.base.JsonMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONAssert.assertEquals
import org.skyscreamer.jsonassert.JSONCompareMode
import org.skyscreamer.jsonassert.JSONCompareMode.LENIENT
import org.skyscreamer.jsonassert.JSONCompareMode.STRICT

class ListWrapperSerializerTest {
    @Test
    fun `should serialize list of primitives`() {
        val input = ListWrapper(Integer::class.java, listOf(1 as Integer, 3 as Integer, 5 as Integer))
        val result = JsonMapper.toJson(input)
        val expected = """{
              "type" : "Integer",
              "items" : [ 1, 3, 5 ]
            }"""
        assertEquals(expected, result, STRICT)
    }

    @Test
    fun `should serialize list of objects`() {
        val input = ListWrapper(Value::class.java, listOf(Value("X"), Value("Y")))
        val result = JsonMapper.toJson(input)
        val expected = """{
              "type": "Value",
              "items": [
                { "value": "X" },
                { "value": "Y" }
              ]
            }"""
        assertEquals(expected, result, STRICT)
    }

    @Test
    fun `should serialize list of hierarchical objects`() {
        val input = ListWrapper(IfaceValue::class.java, listOf(IfaceValueA("A"), IfaceValueB("B"), IfaceValueA("C")))
        val result = JsonMapper.toJson(input)
        val expected = """{
                "type": "IfaceValue",
                "items": [
                  { "type": "IfaceValueA", "a": "A" },
                  { "type": "IfaceValueB", "b": "B" },
                  { "type": "IfaceValueA", "a": "C" }
                ]
            }"""
        assertEquals(expected, result, STRICT)
    }
}
