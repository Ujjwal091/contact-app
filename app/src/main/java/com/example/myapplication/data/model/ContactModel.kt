package com.example.myapplication.data.model

data class ContactModel(
    val id: String,
    val name: String,
    val phone: String,
    val email: String? = null,
    val address: String? = null,
    val company: String? = null,
    val imageUrl: String? = null
)
