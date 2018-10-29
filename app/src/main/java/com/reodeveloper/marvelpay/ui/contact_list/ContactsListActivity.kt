package com.reodeveloper.marvelpay.ui.contact_list

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.reodeveloper.common.RepositoryProvider
import com.reodeveloper.common.usecase.Executor
import com.reodeveloper.common.usecase.ResultList
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.domain.usecase.GetAllContacts


class ContactsListActivity : AppCompatActivity() {
    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 173
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            retreiveContacts()
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                retreiveContacts()
            } else {
                Log.e("Contact", "Permission NOT granted")
            }
        }
    }

    private fun retreiveContacts() {
        Executor.getInstance()
            .execute(GetAllContacts(RepositoryProvider.provideContactRepository(this)), object : ResultList<Contact> {
                override fun success(items: List<Contact>) {
                    items.forEach { Log.d("Contact", it.name + " - " + it.phone + " - " + it.avatar) }
                }

                override fun error(message: String) {
                    Log.d("Contact", message)
                }
            })
    }
}
