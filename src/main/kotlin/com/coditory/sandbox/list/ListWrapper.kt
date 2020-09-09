package com.coditory.sandbox.list

import com.fasterxml.jackson.databind.annotation.JsonDeserialize

@JsonDeserialize(using = ListWrapperDeserializer::class)
data class ListWrapper<T>(
    val items: List<T>
)
