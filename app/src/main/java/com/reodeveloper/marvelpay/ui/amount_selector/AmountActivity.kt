package com.reodeveloper.marvelpay.ui.amount_selector

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.ui.confirmation.ConfirmationActivity
import com.reodeveloper.marvelpay.ui.showMessage
import kotlinx.android.synthetic.main.activity_amount.*


class AmountActivity : AppCompatActivity(), AmountContract.View {

    lateinit var presenter: AmountPresenter

    companion object {
        fun startActivity(context: Context) {
            Intent(context, AmountActivity::class.java).also { context.startActivity(it) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_amount)
        presenter = AmountPresenter(this)

        btn_next_step.setOnClickListener { presenter.onNext() }

        edit_amount.filters = arrayOf(MoneyInputFilter())
        edit_amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                presenter.onAmountChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                edit_amount.error = null
            }
        })
    }

    override fun getCurrentAmount(): String {
        return edit_amount.text.toString()
    }

    override fun showError(message: String) {
        showMessage(message)
    }

    override fun showAmountError(message: String) {
        edit_amount.error = message
    }

    override fun enableNext(value: Boolean) {
        btn_next_step.isEnabled = value
    }

    override fun goToNext(amount: Float) {
        ConfirmationActivity.startActivity(this, amount)
    }
}