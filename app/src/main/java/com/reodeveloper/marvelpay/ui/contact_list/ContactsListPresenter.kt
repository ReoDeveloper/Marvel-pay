package com.reodeveloper.marvelpay.ui.contact_list

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.reodeveloper.common.usecase.Executor
import com.reodeveloper.common.usecase.ResultList
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.domain.usecase.GetAllContacts

class ContactsListPresenter(val view: ContactsListContract.View, val getAllContacts: GetAllContacts) :
    ContactsListContract.Actions {

    companion object {
        val PERMISSIONS_REQUEST_READ_CONTACTS = 173
    }

    override fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.requestPermissions()
        } else {
            onPermissionGranted()
        }
    }

    override fun onItemTap(item: Contact) {

    }

    override fun getRequestPermissionCode(): Int {
        return PERMISSIONS_REQUEST_READ_CONTACTS
    }

    override fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted()
            } else {
                view.showError(R.string.txt_error_read_contacts)
            }
        }
    }

    override fun onPermissionGranted() {
        view.showLoading()
        Executor.getInstance()
            .execute(
                getAllContacts,
                object : ResultList<Contact> {
                    override fun success(items: List<Contact>) {
                        view.displayItems(items)
                        view.hideLoading()
                    }

                    override fun error(message: String) {
                        view.showError(message)
                        view.hideLoading()
                    }
                })
    }
}