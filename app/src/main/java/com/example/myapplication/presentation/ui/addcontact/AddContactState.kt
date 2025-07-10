package com.example.myapplication.presentation.ui.addcontact

/**
 * Represents the state of the add contact screen
 */
sealed class AddContactState {
    /**
     * Initial state
     */
    object Initial : AddContactState()

    /**
     * Loading state when adding a contact
     */
    object Loading : AddContactState()

    /**
     * Success state when a contact is successfully added
     */
    object Success : AddContactState()

    /**
     * Error state when there's an error adding a contact
     *
     * @param message The error message
     */
    data class Error(val message: String) : AddContactState()

    /**
     * Form state with field-specific errors
     *
     * @param nameError Error message for the name field, null if no error
     * @param phoneError Error message for the phone field, null if no error
     */
    data class FormError(
        val nameError: String? = null,
        val phoneError: String? = null
    ) : AddContactState()
}
