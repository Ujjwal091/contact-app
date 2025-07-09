package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.repository.ContactRepository

class UpdateContactUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(contact: Contact): Boolean = repository.updateContact(contact)
}
