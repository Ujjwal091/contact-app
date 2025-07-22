package com.example.myapplication.presentation.ui.addcontact

import com.example.myapplication.domain.entity.Contact

/**
 * Represents the state of the add/edit contact screen
 */
sealed class AddContactState {
    /**
     * Initial state
     */
    object Initial : AddContactState()

    /**
     * Loading state when adding or updating a contact
     */
    object Loading : AddContactState()

    /**
     * Success state when a contact is successfully added or updated
     */
    object Success : AddContactState()

    /**
     * Error state when there's an error adding or updating a contact
     *
     * @param message The error message
     */
    data class Error(val message: String) : AddContactState()

    /**
     * Form state with field-specific errors
     *
     * @param nameError Error message for the name field, null if no error
     * @param phoneError Error message for the phone field, null if no error
     * @param emailError Error message for the email field, null if no error
     * @param companyError Error message for the company field, null if no error
     */
    data class FormError(
        val nameError: String? = null,
        val phoneError: String? = null,
        val emailError: String? = null,
        val companyError: String? = null
    ) : AddContactState()

    /**
     * Editing state when contact details are loaded and ready for editing
     *
     * @param contact The contact being edited
     */
    data class Editing(val contact: Contact) : AddContactState()

    /**
     * State when the contact is not found
     */
    object NotFound : AddContactState()
}
