package com.reodeveloper.marvelpay.ui.contact_list

import android.content.pm.PackageManager
import android.os.Build
import com.reodeveloper.common.UseCaseProvider
import com.reodeveloper.common.usecase.Executor
import com.reodeveloper.common.usecase.ResultList
import com.reodeveloper.common.usecase.ResultUnit
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.domain.usecase.GetAllContactsByPage

class ContactsListPresenter(val view: ContactsListContract.View, val getAllContactsByPage: GetAllContactsByPage) :
    ContactsListContract.Actions {

    var hasMorePages = true
    var isAlreadyLoading = false

    var selectedItems: ArrayList<Contact> = ArrayList()

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

    override fun refresh() {
        view.displayItems(emptyList())
    }

    override fun onItemTap(item: Contact) {
        if (selectedItems.contains(item)) selectedItems.remove(item) else selectedItems.add(item)
        view.enableNext(selectedItems.size != 0)
        view.showItemsCount(selectedItems.size)
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
        isAlreadyLoading = true
        Executor.getInstance()
            .execute(
                getAllContactsByPage,
                object : ResultList<Contact> {
                    override fun success(items: List<Contact>) {
                        view.displayItems(items)
                        view.hideLoading()
                        isAlreadyLoading = false
                    }

                    override fun error(message: String) {
                        view.showError(message)
                        view.hideLoading()
                        isAlreadyLoading = false
                    }
                })
    }

    override fun lastItemReached() {
        if (hasMorePages && !isAlreadyLoading) {
            view.showLoading()
            isAlreadyLoading = true
            getAllContactsByPage.page++
            Executor.getInstance()
                .execute(
                    getAllContactsByPage,
                    object : ResultList<Contact> {
                        override fun success(items: List<Contact>) {
                            if (items.isNotEmpty()) {
                                view.addItems(items)
                            } else {
                                hasMorePages = false
                            }
                            view.hideLoading()
                            isAlreadyLoading = false
                        }

                        override fun error(message: String) {
                            view.showError(message)
                            view.hideLoading()
                            isAlreadyLoading = false
                        }
                    })
        }
    }

    override fun onNext() {
        if (selectedItems.size > 0) {
            Executor.getInstance().execute(
                UseCaseProvider.provideCacheCheckedContacts(view.getContext(), selectedItems),
                object : ResultUnit<Contact> {
                    override fun success() {
                        view.goToNext()
                    }

                    override fun error(message: String) {
                        view.showError(message)
                    }
                })
        } else {
            view.showError(R.string.txt_error_no_selected)
        }
    }
}