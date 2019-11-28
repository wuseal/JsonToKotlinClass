package com.theapache64.j2k.enums

enum class PropertyType {

    NON_NULLABLE,
    NULLABLE,

    /**
     * Auto determine nullable or not from JSON value
     */
    AUTO
}