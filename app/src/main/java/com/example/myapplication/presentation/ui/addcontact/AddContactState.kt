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
}