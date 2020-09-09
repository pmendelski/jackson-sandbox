package com.coditory.sandbox.list

import com.coditory.sandbox.base.JsonMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ListWrapperDeserializerTest {
    @Test
    fun `should deserialize list of primitives`() {
        val input =
            """
            {
                "type": "Integer",
                "items": [1, 3, 5]
            }
            """
        val result = JsonMapper.parseJson(input, ListWrapper::class.java)
        val expected = ListWrapper(Integer::class.java, listOf(1 as Integer, 3 as Integer, 5 as Integer))
        assertEquals(expected, result)
    }

    @Test
    fun `should deserialize list of objects`() {
        val input =
            """
            {
                "type": "Value",
                "items": [
                    { "value": "X" },
                    { "value": "Y" }
                ]
            }
            """
        val result = JsonMapper.parseJson(input, ListWrapper::class.java)
        val expected = ListWrapper(Value::class.java, listOf(Value("X"), Value("Y")))
        assertEquals(expected, result)
    }

    @Test
    fun `should deserialize list of hierarchical objects`() {
        val input =
            """
            {
                "type": "IfaceValue",
                "items": [
                  { "type": "IfaceValueA", "a": "A" },
                  { "type": "IfaceValueB", "b": "B" },
                  { "type": "IfaceValueA", "a": "C" }
                ]
            }
            """
        val result = JsonMapper.parseJson(input, ListWrapper::class.java)
        val expected = ListWrapper(IfaceValue::class.java, listOf(IfaceValueA("A"), IfaceValueB("B"), IfaceValueA("C")))
        assertEquals(expected, result)
    }
}
