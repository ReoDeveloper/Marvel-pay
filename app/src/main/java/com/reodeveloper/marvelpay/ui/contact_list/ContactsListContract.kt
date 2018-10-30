package com.reodeveloper.marvelpay.ui.contact_list

import com.reodeveloper.marvelpay.domain.model.Contact

interface ContactsListContract {
    interface View {
        fun showError(message: String)
        fun showError(message: Int)
        fun displayItems(items: List<Contact>)
        fun showLoading()
        fun hideLoading()
        fun requestPermissions()
    }

    interface Actions {
        fun init()
        fun onItemTap(item: Contact)
        fun getRequestPermissionCode(): Int
        fun onPermissionGranted()
        fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
    }
}