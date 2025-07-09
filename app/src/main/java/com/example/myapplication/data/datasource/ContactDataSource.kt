package com.example.myapplication.data.datasource

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.ContactsContract
import com.example.myapplication.data.model.ContactModel

class ContactDataSource(private val contentResolver: ContentResolver) {

    fun fetchAllContacts(): List<ContactModel> {
        val contacts = mutableListOf<ContactModel>()
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val phoneIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                contacts.add(
                    ContactModel(
                        id = it.getString(idIndex),
                        name = it.getString(nameIndex),
                        phone = it.getString(phoneIndex)
                    )
                )
            }
        }
        return contacts
    }

    fun addContact(contact: ContactModel): Boolean {
        try {
            val values = ContentValues().apply {
                put(ContactsContract.RawContacts.ACCOUNT_TYPE, null as String?)
                put(ContactsContract.RawContacts.ACCOUNT_NAME, null as String?)
            }

            val rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
            val rawContactId = rawContactUri?.lastPathSegment?.toLongOrNull() ?: return false

            val nameValues = ContentValues().apply {
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contact.name)
            }
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, nameValues)

            val phoneValues = ContentValues().apply {
                put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId)
                put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                put(ContactsContract.CommonDataKinds.Phone.NUMBER, contact.phone)
                put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
            }
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, phoneValues)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun deleteContact(id: String): Boolean {
        val uri = ContactsContract.RawContacts.CONTENT_URI
        val where = ContactsContract.RawContacts.CONTACT_ID + " = ?"
        val args = arrayOf(id)
        val deletedRows = contentResolver.delete(uri, where, args)
        return deletedRows > 0
    }

    /**
     * Updates an existing contact in the device's contacts database.
     * 
     * @param contact The contact model containing updated information
     * @return true if the update was successful, false otherwise
     */
    fun updateContact(contact: ContactModel): Boolean {
        try {
            // First, find the raw contact ID for this contact
            val rawContactId = getRawContactId(contact.id) ?: return false

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

            return nameUpdateCount > 0 || phoneUpdateCount > 0
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
