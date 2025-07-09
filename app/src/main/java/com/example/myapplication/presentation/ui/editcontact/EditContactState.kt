package com.example.myapplication.presentation.ui.editcontact

import com.example.myapplication.domain.entity.Contact

/**
 * Represents the state of the edit contact screen
 */
sealed class EditContactState {
    /**
     * Loading state when fetching contact details or updating a contact
     */
    object Loading : EditContactState()

    /**
     * Editing state when contact details are loaded and ready for editing
     *
     * @param contact The contact being edited
     */
    data class Editing(val contact: Contact) : EditContactState()

    /**
     * Success state when a contact is successfully updated
     */
    object Success : EditContactState()

    /**
     * Error state when there's an error loading or updating a contact
     *
     * @param message The error message
     */
    data class Error(val message: String) : EditContactState()

    /**
     * State when the contact is not found
     */
    object NotFound : EditContactState()
}