package com.reodeveloper.marvelpay.ui.confirmation

import com.reodeveloper.common.usecase.Executor
import com.reodeveloper.common.usecase.ResultList
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.domain.usecase.GetAllCheckedContacts

class ConfirmationPresenter(val view: ConfirmationContract.View, val getAllCheckedContacts: GetAllCheckedContacts) :
    ConfirmationContract.Actions {

    override fun init(amount: Float) {
        if(amount == 0.0f){
            view.showError("Invalid amount")
            return
        }
        Executor.getInstance().execute(
            getAllCheckedContacts,
            object : ResultList<Contact> {
                override fun success(items: List<Contact>) {
                    view.displayItems(mapToViewModel(amount, items))
                }

                override fun error(message: String) {
                    view.showError(message)
                }
            })
    }

    private fun mapToViewModel(amount: Float, items: List<Contact>): List<ConfirmationViewModel> {
        return items.map { ConfirmationViewModel(it, amount/items.size) }
    }

}