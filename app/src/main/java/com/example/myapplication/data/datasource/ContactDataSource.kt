package com.example.myapplication.data.datasource

import com.example.myapplication.data.model.BasicContactModel
import com.example.myapplication.data.model.ContactModel

/**
 * Interface for accessing contact data from the device's contacts database.
 */
interface ContactDataSource {
    /**
     * Fetches all contacts from the device's contacts database.
     * Uses a lightweight model to optimize memory usage.
     *
     * @return A list of basic contact models with only essential properties
     */
    suspend fun fetchAllContacts(): List<BasicContactModel>

    /**
     * Fetches a contact by its ID from the device's contacts database.
     *
     * @param id The contact ID
     * @return The contact model, or null if not found
     */
    suspend fun fetchContactById(id: String): ContactModel?

    /**
     * Adds a new contact to the device's contacts database.
     *
     * @param contact The contact model to add
     * @return true if the addition was successful, false otherwise
     */
    suspend fun addContact(contact: ContactModel): Boolean

    /**
     * Updates an existing contact in the device's contacts database.
     *
     * @param contact The contact model containing updated information
     * @return true if the update was successful, false otherwise
     */
    suspend fun updateContact(contact: ContactModel): Boolean

    /**
     * Deletes a contact from the device's contacts database.
     *
     * @param id The ID of the contact to delete
     * @return true if the deletion was successful, false otherwise
     */
    suspend fun deleteContact(id: String): Boolean
}