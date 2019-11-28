package com.theapache64.j2k.enums

enum class PropertyDefaultValueStrategy {
    DONT_INIT_WITH_DEFAULT_VALUE,
    INIT_WITH_NON_NULL_DEFAULT_VALUE,
    INIT_WITH_DEFAULT_VALUE_NULL_WHEN_PROPERTY_IS_NULLABLE
}