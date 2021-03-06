package com.reodeveloper.marvelpay.ui.contact_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.reodeveloper.marvelpay.R
import com.reodeveloper.marvelpay.domain.model.Contact
import com.reodeveloper.marvelpay.ui.inflate
import com.reodeveloper.marvelpay.ui.loadUrl
import kotlinx.android.synthetic.main.contact_list_item.view.*
import android.text.method.TextKeyListener.clear



class ContactsAdapter(val characters: MutableList<Contact>, private val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    fun addItems(items: List<Contact>) {
        characters.addAll(items)
        notifyDataSetChanged()
    }

    fun clear() {
        characters.clear()
        notifyDataSetChanged()
    }

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
            img_tick.visibility = if (item.selected) View.VISIBLE else View.INVISIBLE
            setOnClickListener {
                item.selected = !item.selected
                listener(item)
            }
        }
    }
}