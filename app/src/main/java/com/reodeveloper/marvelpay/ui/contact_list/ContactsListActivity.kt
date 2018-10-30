package com.reodeveloper.marvelpay.ui.contact_list

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.reodeveloper.common.UseCaseProvider
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.ui.showMessage
import kotlinx.android.synthetic.main.activity_contacts_list.*


class ContactsListActivity : AppCompatActivity(), ContactsListContract.View {

    private lateinit var presenter: ContactsListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        recyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?

        btn_next_step.setOnClickListener { presenter.onNext() }

        presenter = ContactsListPresenter(this, UseCaseProvider.provideGetAllContactsUseCase(this))
        presenter.init()
    }

    override fun showError(message: String) {
        this.showMessage(message)
    }

    override fun showError(message: Int) {
        this.showMessage(getString(message))
    }

    override fun displayItems(items: List<Contact>) {
        recyclerView.adapter = ContactsAdapter(items) {
            presenter.onItemTap(it)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    presenter.getRequestPermissionCode()
                )
            } else {
                presenter.onPermissionGranted()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        presenter.onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun enableNext(value: Boolean) {
        btn_next_step.isEnabled = value
    }

    override fun showItemsCount(count: Int) {
        txt_selected_count.visibility = View.VISIBLE
        txt_selected_count.text = resources.getQuantityString(R.plurals.txt_selected_items, count, count)
    }

    override fun getContext(): Context {
        return this
    }

    override fun goToNext() {
        showMessage("go To Next")
    }
}
