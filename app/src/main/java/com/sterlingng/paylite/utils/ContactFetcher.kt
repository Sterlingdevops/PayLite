package com.sterlingng.paylite.utils

import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import com.sterlingng.paylite.data.model.Contact
import java.util.*


class ContactFetcher(private val context: Context) {
    private val listContacts = ArrayList<Contact>()

    fun onLoadComplete(loader: Loader<Cursor>, c: Cursor?) {
        val contactsMap = HashMap<String, Contact>(c!!.count)

        if (c.moveToFirst()) {
            val idIndex = c.getColumnIndex(ContactsContract.Data._ID)
            val nameIndex = c.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)

            do {
                val contactId = c.getString(idIndex)
                val contactDisplayName = c.getString(nameIndex)
                val contact = Contact(contactId, contactDisplayName)
                contactsMap[contactId] = contact
                listContacts.add(contact)
            } while (c.moveToNext())
        }

        c.close()

        matchContactNumbers(contactsMap)
        matchContactEmails(contactsMap)
    }

    fun fetchAll(): ArrayList<Contact> {
        val projectionFields = arrayOf(
                ContactsContract.Data._ID,
                ContactsContract.Data.LOOKUP_KEY,
                ContactsContract.Data.DISPLAY_NAME
        )
        val cursorLoader = CursorLoader(context,
                ContactsContract.Contacts.CONTENT_URI,
                projectionFields, null, null, null// the sort order (default)
        )// the columns to retrieve
        // the selection criteria (none)
        // the selection args (none)

        cursorLoader.loadInBackground()
        return listContacts
    }

    fun matchContactNumbers(contactsMap: Map<String, Contact>) {
        // Get numbers
        val numberProjection = arrayOf(Phone.NUMBER, Phone.TYPE, Phone.CONTACT_ID)

        val phone = CursorLoader(context,
                Phone.CONTENT_URI,
                numberProjection, null, null, null).loadInBackground()

        if (phone!!.moveToFirst()) {
            val contactNumberColumnIndex = phone.getColumnIndex(Phone.NUMBER)
            val contactTypeColumnIndex = phone.getColumnIndex(Phone.TYPE)
            val contactIdColumnIndex = phone.getColumnIndex(Phone.CONTACT_ID)

            while (!phone.isAfterLast) {
                val number = phone.getString(contactNumberColumnIndex)
                val contactId = phone.getString(contactIdColumnIndex)
                val contact = contactsMap[contactId] ?: continue
                val type = phone.getInt(contactTypeColumnIndex)
                val customLabel = "Custom"
                val phoneType = ContactsContract.CommonDataKinds.Phone.getTypeLabel(context.resources, type, customLabel)
                contact.addNumber(number, phoneType.toString())
                phone.moveToNext()
            }
        }
        phone.close()
    }

    fun matchContactEmails(contactsMap: Map<String, Contact>) {
        // Get email
        val emailProjection = arrayOf(Email.DATA, Email.TYPE, Email.CONTACT_ID)

        val email = CursorLoader(context,
                Email.CONTENT_URI,
                emailProjection, null, null, null).loadInBackground()

        if (email!!.moveToFirst()) {
            val contactEmailColumnIndex = email.getColumnIndex(Email.DATA)
            val contactTypeColumnIndex = email.getColumnIndex(Email.TYPE)
            val contactIdColumnsIndex = email.getColumnIndex(Email.CONTACT_ID)

            while (!email.isAfterLast) {
                val address = email.getString(contactEmailColumnIndex)
                val contactId = email.getString(contactIdColumnsIndex)
                val type = email.getInt(contactTypeColumnIndex)
                val customLabel = "Custom"
                val contact = contactsMap[contactId] ?: continue
                val emailType = ContactsContract.CommonDataKinds.Email.getTypeLabel(context.resources, type, customLabel)
                contact.addEmail(address, emailType.toString())
                email.moveToNext()
            }
        }
        email.close()
    }
}