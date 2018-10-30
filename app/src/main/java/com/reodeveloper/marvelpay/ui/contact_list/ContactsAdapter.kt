package com.reodeveloper.marvelpay.ui.contact_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.ui.inflate
import com.reodeveloper.marvelpay.ui.loadUrl
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactsAdapter(val characters: List<Contact>, private val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.contact_list_item))

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(characters[position], listener)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Contact, listener: (Contact) -> Unit) = with(itemView) {
            txt_name.text = item.name
            txt_phone.text = item.phone
            img_avatar.loadUrl(item.avatar)
            setOnClickListener { listener(item) }
        }
    }
}