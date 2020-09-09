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
        val expected = ListWrapper(listOf(1, 3, 5))
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
        val expected = ListWrapper(listOf(Value("X"), Value("Y")))
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
        val expected = ListWrapper(listOf(IfaceValueA("A"), IfaceValueB("B"), IfaceValueA("C")))
        assertEquals(expected, result)
    }
}
