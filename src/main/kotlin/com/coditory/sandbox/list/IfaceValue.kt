package com.coditory.sandbox.list

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = IfaceValueA::class, name = "IfaceValueA"),
    JsonSubTypes.Type(value = IfaceValueB::class, name = "IfaceValueB")
)
interface IfaceValue

data class IfaceValueA(val a: String) : IfaceValue

data class IfaceValueB(val b: String) : IfaceValue
