package com.danielp4.recyclerapp.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielp4.recyclerapp.R
import com.danielp4.recyclerapp.data.ContactModel
import com.danielp4.recyclerapp.databinding.ContactItemBinding
import com.danielp4.recyclerapp.utils.ItemMoveCallback

class ContactAdapter(
    val listener: Listener
) : ListAdapter<ContactModel, ContactAdapter.ContactHolder>(ContactDiffCallback()), ItemMoveCallback.ItemTouchHelperAdapter {

    class ContactHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ContactItemBinding.bind(item)
        fun bind(contact: ContactModel, listener: Listener) = with(binding) {
            tvId.text = contact.id.toString()
            tvFirstName.text = contact.firstName
            tvSecondName.text = contact.secondName
            tvPhone.text = contact.phoneNumber
            val colorResId = if (contact.isTurn) R.color.turn_contact else R.color.white
            cardContact.setCardBackgroundColor(ContextCompat.getColor(itemView.context, colorResId))
            itemView.setOnClickListener {
                listener.onClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactHolder(view)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact, listener)
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<ContactModel>() {
        override fun areItemsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ContactModel, newItem: ContactModel): Boolean {
            return oldItem == newItem
        }

    }

    interface Listener {
        fun onClick(contact: ContactModel)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }

}
