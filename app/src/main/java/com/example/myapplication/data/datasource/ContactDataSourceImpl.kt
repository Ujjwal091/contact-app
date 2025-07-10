package com.example.myapplication.data.datasource

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.ContactsContract
import com.example.myapplication.data.model.ContactModel

/**
 * Implementation of ContactDataSource that uses ContentResolver to access the device's contacts database.
 */
class ContactDataSourceImpl(private val contentResolver: ContentResolver) : ContactDataSource {

    override fun fetchAllContacts(): List<ContactModel> {
        val contactsMap = mutableMapOf<String, ContactModel>()

        // First, get basic contact info (ID, name, phone)
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                // Only add the contact if we haven't seen this ID before
                if (!contactsMap.containsKey(id)) {
                    contactsMap[id] = ContactModel(
                        id = id,
                        name = it.getString(nameIndex),
                        phone = it.getString(phoneIndex),
                        email = null,
                        address = null,
                        company = null,
                        imageUrl = null
                    )
                }
            }
        }

        // Return the basic contact info without fetching additional details
        // This makes the initial load much faster
        return contactsMap.values.toList()
    }

    override fun fetchContactById(id: String): ContactModel? {
        var name = ""
        var phone = ""
        var email: String? = null
        var address: String? = null
        var company: String? = null
        var imageUrl: String? = null

        // Query for basic contact information
        val contactUri = ContactsContract.Contacts.CONTENT_URI
        val contactSelection = "${ContactsContract.Contacts._ID} = ?"
        val contactSelectionArgs = arrayOf(id)
        val contactCursor = contentResolver.query(
            contactUri,
            null,
            contactSelection,
            contactSelectionArgs,
            null
        )

        contactCursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }

                // Get photo URI if available
                val photoUriIndex = it.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
                if (photoUriIndex != -1) {
                    imageUrl = it.getString(photoUriIndex)
                }

                // Check if contact has phone numbers
                val hasPhoneIndex = it.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                if (hasPhoneIndex != -1 && it.getInt(hasPhoneIndex) > 0) {
                    // Query for phone numbers
                    val phoneCursor = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                        arrayOf(id),
                        null
                    )

                    phoneCursor?.use { phoneCur ->
                        if (phoneCur.moveToFirst()) {
                            val phoneIndex = phoneCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            if (phoneIndex != -1) {
                                phone = phoneCur.getString(phoneIndex)
                            }
                        }
                    }
                }
            } else {
                // Contact not found
                return null
            }
        }

        // Query for email
        val emailCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.Email.CONTACT_ID} = ?",
            arrayOf(id),
            null
        )

        emailCursor?.use {
            if (it.moveToFirst()) {
                val emailIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
                if (emailIndex != -1) {
                    email = it.getString(emailIndex)
                }
            }
        }

        // Query for postal address
        val addressCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
            null,
            "${ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID} = ?",
            arrayOf(id),
            null
        )

        addressCursor?.use {
            if (it.moveToFirst()) {
                val addressIndex =
                    it.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS)
                if (addressIndex != -1) {
                    address = it.getString(addressIndex)
                }
            }
        }

        // Query for organization/company
        val orgCursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            null,
            "${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
            arrayOf(id, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE),
            null
        )

        orgCursor?.use {
            if (it.moveToFirst()) {
                val companyIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)
                if (companyIndex != -1) {
                    company = it.getString(companyIndex)
                }
            }
        }

        return ContactModel(
            id = id,
            name = name,
            phone = phone,
            email = email,
            address = address,
            company = company,
            imageUrl = imageUrl
        )
    }

    override fun addContact(contact: ContactModel): Boolean {
        try {
            val values = ContentValues().apply {
                put(ContactsContract.RawContacts.ACCOUNT_TYPE, null as String?)
                put(ContactsContract.RawContacts.ACCOUNT_NAME, null as String?)
            }

            val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
            val rawContactId = rawContactUri?.lastPathSegment?.toLongOrNull() ?: return false

            // Add name
            val nameValues = ContentValues().apply {
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.name)
            }
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, nameValues)

            // Add phone
            val phoneValues = ContentValues().apply {
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phone)
                put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            }
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneValues)

            // Add email if available
            contact.email?.let { email ->
                val emailValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                    put(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                }
                contentResolver.insert(ContactsContract.Data.CONTENT_URI, emailValues)
            }

            // Add address if available
            contact.address?.let { address ->
                val addressValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    put(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
                    )
                    put(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, address)
                    put(
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME
                    )
                }
                contentResolver.insert(ContactsContract.Data.CONTENT_URI, addressValues)
            }

            // Add company if available
            contact.company?.let { company ->
                val companyValues = ContentValues().apply {
                    put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                    put(
                        ContactsContract.CommonDataKinds.Organization.TYPE,
                        ContactsContract.CommonDataKinds.Organization.TYPE_WORK
                    )
                }
                contentResolver.insert(ContactsContract.Data.CONTENT_URI, companyValues)
            }

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    override fun deleteContact(id: String): Boolean {
        val uri = ContactsContract.RawContacts.CONTENT_URI
        val where = ContactsContract.RawContacts.CONTACT_ID + " = ?"
        val args = arrayOf(id)
        val deletedRows = contentResolver.delete(uri, where, args)
        return deletedRows > 0
    }

    override fun updateContact(contact: ContactModel): Boolean {
        try {
            // First, find the raw contact ID for this contact
            val rawContactId = getRawContactId(contact.id) ?: return false
            var updateSuccess = false

            // Update name
            val nameWhere = "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
            val nameArgs = arrayOf(
                rawContactId.toString(),
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )

            val nameValues = ContentValues().apply {
                put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.name)
            }

            val nameUpdateCount = contentResolver.update(
                ContactsContract.Data.CONTENT_URI,
                nameValues,
                nameWhere,
                nameArgs
            )
            updateSuccess = updateSuccess || nameUpdateCount > 0

            // Update phone number
            val phoneWhere = "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
            val phoneArgs = arrayOf(
                rawContactId.toString(),
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )

            val phoneValues = ContentValues().apply {
                put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phone)
            }

            val phoneUpdateCount = contentResolver.update(
                ContactsContract.Data.CONTENT_URI,
                phoneValues,
                phoneWhere,
                phoneArgs
            )
            updateSuccess = updateSuccess || phoneUpdateCount > 0

            // Update or add email
            contact.email?.let { email ->
                val emailWhere = "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
                val emailArgs = arrayOf(
                    rawContactId.toString(),
                    ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
                )

                val emailValues = ContentValues().apply {
                    put(ContactsContract.CommonDataKinds.Email.ADDRESS, email)
                }

                // Check if email exists
                val emailCursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    emailWhere,
                    emailArgs,
                    null
                )

                if ((emailCursor?.use { it.count } ?: 0) > 0) {
                    // Update existing email
                    val emailUpdateCount = contentResolver.update(
                        ContactsContract.Data.CONTENT_URI,
                        emailValues,
                        emailWhere,
                        emailArgs
                    )
                    updateSuccess = updateSuccess || emailUpdateCount > 0
                } else {
                    // Add new email
                    emailValues.apply {
                        put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                        put(
                            ContactsContract.CommonDataKinds.Email.TYPE,
                            ContactsContract.CommonDataKinds.Email.TYPE_HOME
                        )
                    }
                    val emailUri = contentResolver.insert(ContactsContract.Data.CONTENT_URI, emailValues)
                    updateSuccess = updateSuccess || (emailUri != null)
                }
            }

            // Update or add address
            contact.address?.let { address ->
                val addressWhere =
                    "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
                val addressArgs = arrayOf(
                    rawContactId.toString(),
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
                )

                val addressValues = ContentValues().apply {
                    put(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS, address)
                }

                // Check if address exists
                val addressCursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    addressWhere,
                    addressArgs,
                    null
                )

                if ((addressCursor?.use { it.count } ?: 0) > 0) {
                    // Update existing address
                    val addressUpdateCount = contentResolver.update(
                        ContactsContract.Data.CONTENT_URI,
                        addressValues,
                        addressWhere,
                        addressArgs
                    )
                    updateSuccess = updateSuccess || addressUpdateCount > 0
                } else {
                    // Add new address
                    addressValues.apply {
                        put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        put(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE
                        )
                        put(
                            ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                            ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME
                        )
                    }
                    val addressUri = contentResolver.insert(ContactsContract.Data.CONTENT_URI, addressValues)
                    updateSuccess = updateSuccess || (addressUri != null)
                }
            }

            // Update or add company
            contact.company?.let { company ->
                val companyWhere =
                    "${ContactsContract.Data.RAW_CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?"
                val companyArgs = arrayOf(
                    rawContactId.toString(),
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                )

                val companyValues = ContentValues().apply {
                    put(ContactsContract.CommonDataKinds.Organization.COMPANY, company)
                }

                // Check if company exists
                val companyCursor = contentResolver.query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    companyWhere,
                    companyArgs,
                    null
                )

                if ((companyCursor?.use { it.count } ?: 0) > 0) {
                    // Update existing company
                    val companyUpdateCount = contentResolver.update(
                        ContactsContract.Data.CONTENT_URI,
                        companyValues,
                        companyWhere,
                        companyArgs
                    )
                    updateSuccess = updateSuccess || companyUpdateCount > 0
                } else {
                    // Add new company
                    companyValues.apply {
                        put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                        put(
                            ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE
                        )
                        put(
                            ContactsContract.CommonDataKinds.Organization.TYPE,
                            ContactsContract.CommonDataKinds.Organization.TYPE_WORK
                        )
                    }
                    val companyUri = contentResolver.insert(ContactsContract.Data.CONTENT_URI, companyValues)
                    updateSuccess = updateSuccess || (companyUri != null)
                }
            }

            return updateSuccess
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    /**
     * Gets the raw contact ID for a given contact ID.
     *
     * @param contactId The contact ID
     * @return The raw contact ID, or null if not found
     */
    private fun getRawContactId(contactId: String): Long? {
        val cursor = contentResolver.query(
            ContactsContract.RawContacts.CONTENT_URI,
            arrayOf(ContactsContract.RawContacts._ID),
            "${ContactsContract.RawContacts.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        return cursor?.use {
            if (it.moveToFirst()) {
                val idIndex = it.getColumnIndex(ContactsContract.RawContacts._ID)
                it.getLong(idIndex)
            } else {
                null
            }
        }
    }
}
