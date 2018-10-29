package com.reodeveloper.marvelpay.data.device

import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract

class ContactResolver(val context: Context) {

    private val contentResolver: ContentResolver = context.contentResolver

    fun getContactsInfo(): List<DeviceContact> {
        val result = ArrayList<DeviceContact>()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null, null
        )
        cursor?.run {
            if (cursor.moveToFirst()) {
                do {
                    val idContact = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                    val avatar = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                    val number: String? = ContactResolver(context).getPhoneNumberFromContact(idContact)

                    result.add(DeviceContact(name, number, avatar))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return result
    }

    private fun getPhoneNumberFromContact(id: String): String? {
        var number: String? = null
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id, null, null
        )
        cursor?.run {
            if (cursor.moveToFirst()) {
                do {
                    number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                } while (cursor.moveToNext())
            }
            cursor.close()
        }
        return number
    }
}