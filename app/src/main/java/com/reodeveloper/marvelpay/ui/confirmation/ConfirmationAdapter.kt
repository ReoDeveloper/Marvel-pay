package com.reodeveloper.marvelpay.ui.confirmation

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.ui.inflate
import com.reodeveloper.marvelpay.ui.loadUrl
import kotlinx.android.synthetic.main.confirmation_list_item.view.*

class ConfirmationAdapter(val items: List<ConfirmationViewModel>) :
    RecyclerView.Adapter<ConfirmationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ConfirmationAdapter.ViewHolder(parent.inflate(R.layout.confirmation_list_item))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ConfirmationAdapter.ViewHolder, position: Int) =
        holder.bind(items[position])

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ConfirmationViewModel) = with(itemView) {
            img_contact.loadUrl(item.contact.avatar)
            txt_contact_name.text = item.contact.name
            txt_contact_amount.text =
                    context.getString(R.string.txt_confirmation_amount, String.format("%.2f", item.amount), "€")
        }
    }
}