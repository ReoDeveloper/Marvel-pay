package com.reodeveloper.marvelpay.ui.amount_selector

interface AmountContract {
    interface View {
        fun showError(message: String)
        fun showAmountError(message: String)
        fun enableNext(value: Boolean)
        fun goToNext()
    }

    interface Actions {
        fun onAmountChanged(content: String)
        fun onNext()
    }
}