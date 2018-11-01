package com.reodeveloper.marvelpay.ui.contact_list

import android.content.Context
import com.reodeveloper.marvelpay.domain.model.Contact

interface ContactsListContract {
    interface View {
        fun showError(message: String)
        fun showError(message: Int)
        fun displayItems(items: List<Contact>)
        fun addItems(items: List<Contact>)
        fun showLoading()
        fun hideLoading()
        fun requestPermissions()
        fun enableNext(value: Boolean)
        fun showItemsCount(count: Int)
        fun getContext(): Context
        fun goToNext()
    }

    interface Actions {
        fun init()
        fun refresh()
        fun onItemTap(item: Contact)
        fun getRequestPermissionCode(): Int
        fun onPermissionGranted()
        fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray)
        fun onNext()
        fun lastItemReached()
    }
}