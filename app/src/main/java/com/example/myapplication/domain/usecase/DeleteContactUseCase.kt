package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.ContactRepository

class DeleteContactUseCase(private val repository: ContactRepository) {
    suspend operator fun invoke(id: String): Boolean = repository.deleteContact(id)
}
