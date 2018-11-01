package com.reodeveloper.marvelpay.ui.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.reodeveloper.common.UseCaseProvider
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.ui.showMessage
import kotlinx.android.synthetic.main.activity_confirmation.*

class ConfirmationActivity : AppCompatActivity(), ConfirmationContract.View {

    lateinit var presenter: ConfirmationPresenter

    companion object {
        const val AMOUNT = "amount"

        fun startActivity(context: Context, amount: Float) {
            Intent(context, ConfirmationActivity::class.java).apply {
                putExtra(AMOUNT, amount)
            }.also { context.startActivity(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        recyclerView.layoutManager = LinearLayoutManager(this)

        presenter = ConfirmationPresenter(this, UseCaseProvider.provideGetAllCheckedContacts(this))
        presenter.init(intent.getFloatExtra(AMOUNT, 0.0f))
    }

    override fun displayItems(items: List<ConfirmationViewModel>) {
        recyclerView.adapter = ConfirmationAdapter(items)
    }

    override fun showError(message: String) {
        showMessage(message)
    }

    override fun showError(message: Int) {
        showMessage(getString(message))
    }

}