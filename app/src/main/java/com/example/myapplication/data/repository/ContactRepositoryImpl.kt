package com.example.myapplication.data.repository

import com.example.myapplication.data.datasource.ContactDataSource
import com.example.myapplication.data.model.toDomain
import com.example.myapplication.data.model.toModel
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val dataSource: ContactDataSource
) : ContactRepository {

    override suspend fun getAllContacts(): List<Contact> =
        dataSource.fetchAllContacts().map { it.toDomain() }

    override suspend fun getContactById(id: String): Contact? =
        dataSource.fetchAllContacts().firstOrNull { it.id == id }?.toDomain()

    override suspend fun addContact(contact: Contact): Boolean =
        dataSource.addContact(contact.toModel())

    override suspend fun updateContact(contact: Contact): Boolean =
        dataSource.updateContact(contact.toModel())

    override suspend fun deleteContact(id: String): Boolean =
        dataSource.deleteContact(id)
}