package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.repository.ContactRepository

class AddContactUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(contact: Contact): Boolean = repository.addContact(contact)
}
