package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.repository.ContactRepository

class GetAllContactsUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(): List<Contact> = repository.getAllContacts()
}