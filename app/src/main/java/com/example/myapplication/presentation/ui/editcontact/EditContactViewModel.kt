package com.example.myapplication.presentation.ui.editcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.usecase.GetContactByIdUseCase
import com.example.myapplication.domain.usecase.UpdateContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the edit contact screen
 *
 * @param getContactByIdUseCase Use case to get a contact by ID
 * @param updateContactUseCase Use case to update a contact
 */
class EditContactViewModel(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<EditContactState>(EditContactState.Loading)
    val state: StateFlow<EditContactState> = _state

    /**
     * Loads a contact by ID
     *
     * @param contactId The ID of the contact to load
     */
    fun loadContact(contactId: String) {
        viewModelScope.launch {
            try {
                _state.value = EditContactState.Loading
                val contact = getContactByIdUseCase(contactId)
                if (contact != null) {
                    _state.value = EditContactState.Editing(contact)
                } else {
                    _state.value = EditContactState.NotFound
                }
            } catch (e: Exception) {
                _state.value = EditContactState.Error("Failed to load contact: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Updates a contact
     *
     * @param contactId The ID of the contact to update
     * @param name The updated name
     * @param phone The updated phone number
     * @param onSuccess Callback when update is successful
     */
    fun updateContact(contactId: String, name: String, phone: String, onSuccess: () -> Unit) {
        // Validate input
        if (name.isBlank()) {
            _state.value = EditContactState.Error("Name cannot be empty")
            return
        }

        if (phone.isBlank()) {
            _state.value = EditContactState.Error("Phone number cannot be empty")
            return
        }

        viewModelScope.launch {
            try {
                _state.value = EditContactState.Loading

                // Get the current contact to preserve other fields
                val currentContact = getContactByIdUseCase(contactId)
                if (currentContact == null) {
                    _state.value = EditContactState.Error("Contact not found")
                    return@launch
                }

                // Create updated contact with same ID but new name and phone
                val updatedContact = currentContact.copy(
                    name = name,
                    phone = phone
                )

                val success = updateContactUseCase(updatedContact)

                if (success) {
                    _state.value = EditContactState.Success
                    onSuccess()
                } else {
                    _state.value = EditContactState.Error("Failed to update contact")
                }
            } catch (e: Exception) {
                _state.value = EditContactState.Error("Error updating contact: ${e.localizedMessage}")
            }
        }
    }
}
