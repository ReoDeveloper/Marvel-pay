package com.reodeveloper.marvelpay.ui.confirmation

interface ConfirmationContract {

    interface View {
        fun showError(message: String)
        fun showError(message: Int)
        fun displayItems(items: List<ConfirmationViewModel>)
    }

    interface Actions {
        fun init(amount: Float)
    }
}