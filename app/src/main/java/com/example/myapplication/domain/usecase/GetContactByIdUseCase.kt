package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.repository.ContactRepository

class GetContactByIdUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(id: String): Contact? = repository.getContactById(id)
}
