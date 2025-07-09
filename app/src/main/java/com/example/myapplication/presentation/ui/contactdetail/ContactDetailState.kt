package com.example.myapplication.presentation.ui.contactdetail

import com.example.myapplication.domain.entity.Contact

/**
 * Represents the state of the contact detail screen
 */
sealed class ContactDetailState {
    /**
     * Loading state when fetching contact details
     */
    object Loading : ContactDetailState()

    /**
     * Success state when contact details are loaded
     *
     * @param contact The contact details
     */
    data class Success(val contact: Contact) : ContactDetailState()

    /**
     * Error state when there's an error loading contact details
     *
     * @param message The error message
     */
    data class Error(val message: String) : ContactDetailState()

    /**
     * State when the contact is not found
     */
    object NotFound : ContactDetailState()
}