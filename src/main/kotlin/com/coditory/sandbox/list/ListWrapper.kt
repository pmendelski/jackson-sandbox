package com.coditory.sandbox.list

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize(using = ListWrapperSerializer::class)
@JsonDeserialize(using = ListWrapperDeserializer::class)
data class ListWrapper<T>(
    val itemsType: Class<T>,
    val items: List<T>
)
