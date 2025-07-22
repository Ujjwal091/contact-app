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

/**
 * Converts a BasicContactModel to a full ContactModel with null values for additional fields.
 * Used when we need to convert from the lightweight model to the full model.
 */
fun BasicContactModel.toContactModel(): ContactModel =
    ContactModel(
        id = id,
        name = name,
        phone = phone,
        email = null,
        address = null,
        company = null,
        imageUrl = null
    )

/**
 * Converts a BasicContactModel directly to a Contact domain entity.
 * Used for efficient mapping in the repository layer.
 */
fun BasicContactModel.toDomain(): Contact =
    Contact(
        id = id,
        name = name,
        phone = phone,
        email = null,
        address = null,
        company = null,
        imageUrl = null
    )
