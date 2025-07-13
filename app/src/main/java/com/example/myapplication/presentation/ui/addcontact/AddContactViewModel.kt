package com.example.myapplication.presentation.ui.addcontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.usecase.AddContactUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * ViewModel for the add contact screen
 *
 * @param addContactUseCase Use case to add a contact
 */
class AddContactViewModel(
    private val addContactUseCase: AddContactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AddContactState>(AddContactState.Initial)
    val state: StateFlow<AddContactState> = _state

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
     * Adds a new contact
     *
     * @param name The name of the contact
     * @param phone The phone number of the contact
     * @param email The email of the contact
     * @param company The company of the contact
     * @param onSuccess Callback when addition is successful
     */
    fun addContact(
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

                val contact = Contact(
                    id = UUID.randomUUID().toString(), // Generate a unique ID
                    name = name,
                    phone = phone,
                    email = email.ifBlank { null },
                    company = company.ifBlank { null },
                )

                val isSuccess = addContactUseCase(contact)

                if (isSuccess) {
                    _state.value = AddContactState.Success
                    onSuccess()
                } else {
                    _state.value = AddContactState.Error("Failed to add contact")
                }
            } catch (e: Exception) {
                _state.value = AddContactState.Error("Error adding contact: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Resets the state to initial
     */
    fun resetState() {
        _state.value = AddContactState.Initial
    }
}
