package com.example.myapplication.data.model

import com.example.myapplication.domain.entity.Contact


fun ContactModel.toDomain(): Contact =
    Contact(
        id = id, 
        name = name, 
        phone = phone,
        email = email,
        address = address,
        company = company,
        imageUrl = imageUrl
    )

fun Contact.toModel(): ContactModel =
    ContactModel(
        id = id, 
        name = name, 
        phone = phone,
        email = email,
        address = address,
        company = company,
        imageUrl = imageUrl
    )
