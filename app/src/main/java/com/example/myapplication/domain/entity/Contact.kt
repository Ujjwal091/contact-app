package com.example.myapplication.domain.entity

data class Contact(
    val id: String,
    val name: String,
    val phone: String,
    val email: String? = null,
    val address: String? = null,
    val company: String? = null,
    val imageUrl: String? = null
)
