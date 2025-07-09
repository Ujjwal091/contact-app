package com.example.myapplication.domain.entity

data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val imageUrl: String? = null
)
