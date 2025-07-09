package com.example.myapplication.domain.repository

import com.example.myapplication.domain.entity.Contact

interface ContactRepository {
    suspend fun getAllContacts(): List<Contact>
    suspend fun getContactById(id: String): Contact?
    suspend fun addContact(contact: Contact): Boolean
    suspend fun updateContact(contact: Contact): Boolean
    suspend fun deleteContact(id: String): Boolean
}