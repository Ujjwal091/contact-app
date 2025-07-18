package com.example.myapplication.presentation.ui.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.usecase.AddContactUseCase
import com.example.myapplication.domain.usecase.GetContactByIdUseCase
import com.example.myapplication.domain.usecase.UpdateContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for the add/edit contact screen
 *
 * @param addContactUseCase Use case to add a contact
 * @param getContactByIdUseCase Use case to get a contact by ID
 * @param updateContactUseCase Use case to update a contact
 */
class AddContactViewModel(
    private val addContactUseCase: AddContactUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val updateContactUseCase: UpdateContactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddContactState>(AddContactState.Initial)
    val state: StateFlow<AddContactState> = _state
    
    // Track whether we're in edit mode
    private var isEditMode = false
    private var contactIdToEdit: String? = null

    /**
     * Validates if a phone number contains only valid characters
     *
     * @param phone The phone number to validate
     * @return true if valid, false otherwise
     */
    private fun isValidPhoneNumber(phone: String): Boolean {
        return phone.all { char ->
            char.isDigit() || char in " ()-+"
        }
    }

    /**
     * Validates if an email address is valid
     *
     * @param email The email address to validate
     * @return true if valid, false otherwise
     */
    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
    
    /**
     * Loads a contact by ID for editing
     *
     * @param contactId The ID of the contact to load
     */
    fun loadContact(contactId: String) {
        isEditMode = true
        contactIdToEdit = contactId
        
        viewModelScope.launch {
            try {
                _state.value = AddContactState.Loading
                val contact = getContactByIdUseCase(contactId)
                if (contact != null) {
                    _state.value = AddContactState.Editing(contact)
                } else {
                    _state.value = AddContactState.NotFound
                }
            } catch (e: Exception) {
                _state.value = AddContactState.Error("Failed to load contact: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Adds a new contact or updates an existing one
     *
     * @param name The name of the contact
     * @param phone The phone number of the contact
     * @param email The email of the contact
     * @param company The company of the contact
     * @param onSuccess Callback when operation is successful
     */
    fun saveContact(
        name: String,
        phone: String,
        email: String = "",
        company: String = "",
        onSuccess: () -> Unit = {}
    ) {
        // Validate input
        val nameError = if (name.isBlank()) "Name cannot be empty" else null
        val phoneError = when {
            phone.isBlank() -> "Phone number cannot be empty"
            !isValidPhoneNumber(phone) -> "Phone number contains invalid characters"
            else -> null
        }
        val emailError = if (email.isNotBlank() && !isValidEmail(email)) {
            "Please enter a valid email address"
        } else null
        val companyError: String? = null // Company is optional, no validation needed

        if (nameError != null || phoneError != null || emailError != null || companyError != null) {
            _state.value = AddContactState.FormError(
                nameError = nameError,
                phoneError = phoneError,
                emailError = emailError,
                companyError = companyError
            )
            return
        }

        viewModelScope.launch {
            try {
                _state.value = AddContactState.Loading

                val isSuccess = if (isEditMode && contactIdToEdit != null) {
                    // Update existing contact
                    val currentContact = getContactByIdUseCase(contactIdToEdit!!)
                    if (currentContact == null) {
                        _state.value = AddContactState.Error("Contact not found")
                        return@launch
                    }
                    
                    // Create updated contact with same ID but new fields
                    val updatedContact = currentContact.copy(
                        name = name,
                        phone = phone,
                        email = email.ifBlank { null },
                        company = company.ifBlank { null },
                    )
                    
                    updateContactUseCase(updatedContact)
                } else {
                    // Add new contact
                    val contact = Contact(
                        id = UUID.randomUUID().toString(), // Generate a unique ID
                        name = name,
                        phone = phone,
                        email = email.ifBlank { null },
                        company = company.ifBlank { null },
                    )
                    
                    addContactUseCase(contact)
                }

                if (isSuccess) {
                    _state.value = AddContactState.Success
                    onSuccess()
                } else {
                    val action = if (isEditMode) "update" else "add"
                    _state.value = AddContactState.Error("Failed to $action contact")
                }
            } catch (e: Exception) {
                val action = if (isEditMode) "updating" else "adding"
                _state.value = AddContactState.Error("Error $action contact: ${e.localizedMessage}")
            }
        }
    }
    
    /**
     * For backward compatibility with existing code
     */
    fun addContact(
        name: String,
        phone: String,
        email: String = "",
        company: String = "",
        onSuccess: () -> Unit = {}
    ) {
        saveContact(name, phone, email, company, onSuccess)
    }

    /**
     * Resets the state to initial or loads contact if in edit mode
     */
    fun resetState() {
        if (isEditMode && contactIdToEdit != null) {
            loadContact(contactIdToEdit!!)
        } else {
            _state.value = AddContactState.Initial
            isEditMode = false
            contactIdToEdit = null
        }
    }
}
