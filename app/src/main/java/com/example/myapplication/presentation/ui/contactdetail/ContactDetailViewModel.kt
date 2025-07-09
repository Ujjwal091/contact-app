package com.example.myapplication.presentation.ui.contactdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.DeleteContactUseCase
import com.example.myapplication.domain.usecase.GetContactByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the contact detail screen
 *
 * @param getContactByIdUseCase Use case to get a contact by ID
 * @param deleteContactUseCase Use case to delete a contact
 */
class ContactDetailViewModel(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ContactDetailState>(ContactDetailState.Loading)
    val state: StateFlow<ContactDetailState> = _state

    /**
     * Loads a contact by ID
     *
     * @param contactId The ID of the contact to load
     */
    fun loadContact(contactId: String) {
        viewModelScope.launch {
            try {
                _state.value = ContactDetailState.Loading
                val contact = getContactByIdUseCase(contactId)
                if (contact != null) {
                    _state.value = ContactDetailState.Success(contact)
                } else {
                    _state.value = ContactDetailState.NotFound
                }
            } catch (e: Exception) {
                _state.value = ContactDetailState.Error("Failed to load contact: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Deletes the current contact
     *
     * @param contactId The ID of the contact to delete
     * @param onSuccess Callback when deletion is successful
     */
    fun deleteContact(contactId: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val success = deleteContactUseCase(contactId)
                if (success) {
                    onSuccess()
                } else {
                    _state.value = ContactDetailState.Error("Failed to delete contact")
                }
            } catch (e: Exception) {
                _state.value = ContactDetailState.Error("Error deleting contact: ${e.localizedMessage}")
            }
        }
    }
}