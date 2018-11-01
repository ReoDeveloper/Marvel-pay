package com.reodeveloper.marvelpay.ui.amount_selector

class AmountPresenter(val view: AmountContract.View) : AmountContract.Actions {

    override fun onAmountChanged(content: String) {
        if (!content.isNullOrEmpty()) {
            if(content.toFloat() in 0.0f..1000.0f){
                view.enableNext(true)
            }else{
                view.enableNext(false)
                view.showAmountError("Maximum amount is 1,000€")
            }
        } else {
            view.showAmountError("Amount cannot be empty!")
        }
    }

    override fun onNext() {
        view.goToNext(view.getCurrentAmount().toFloat())
    }
}