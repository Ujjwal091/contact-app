package com.example.myapplication.data.model

/**
 * A lightweight model for contact data that only contains essential properties.
 * Used for contact list display to optimize memory usage.
 */
data class BasicContactModel(
    val id: String,
    val name: String,
    val phone: String
)