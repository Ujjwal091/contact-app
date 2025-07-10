package com.example.myapplication.data.datasource

import com.example.myapplication.data.model.ContactModel

/**
 * Interface for accessing contact data from the device's contacts database.
 */
interface ContactDataSource {
    /**
     * Fetches all contacts from the device's contacts database.
     *
     * @return A list of contact models
     */
    fun fetchAllContacts(): List<ContactModel>

    /**
     * Fetches a contact by its ID from the device's contacts database.
     *
     * @param id The contact ID
     * @return The contact model, or null if not found
     */
    fun fetchContactById(id: String): ContactModel?

    /**
     * Adds a new contact to the device's contacts database.
     *
     * @param contact The contact model to add
     * @return true if the addition was successful, false otherwise
     */
    fun addContact(contact: ContactModel): Boolean

    /**
     * Updates an existing contact in the device's contacts database.
     *
     * @param contact The contact model containing updated information
     * @return true if the update was successful, false otherwise
     */
    fun updateContact(contact: ContactModel): Boolean

    /**
     * Deletes a contact from the device's contacts database.
     *
     * @param id The ID of the contact to delete
     * @return true if the deletion was successful, false otherwise
     */
    fun deleteContact(id: String): Boolean
}