package com.danielp4.recyclerapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.danielp4.recyclerapp.utils.ContactViewModel
import com.danielp4.recyclerapp.adapters.ContactAdapter
import com.danielp4.recyclerapp.data.ContactModel
import com.danielp4.recyclerapp.databinding.FragmentListContactBinding
import com.danielp4.recyclerapp.utils.Constants.LIST_CONTACT_FRAGMENT_TAG
import com.danielp4.recyclerapp.utils.FragmentManager
import com.danielp4.recyclerapp.utils.ItemMoveCallback


class ListContactFragment : Fragment(), ContactAdapter.Listener {

    private lateinit var binding: FragmentListContactBinding
    private val viewModel: ContactViewModel by activityViewModels()
    private lateinit var adapter: ContactAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        if (viewModel.scrollState.value != null) {
            binding.rcView.scrollToPosition(viewModel.scrollState.value!!)
        }
        if (binding.bAdd.visibility == View.VISIBLE) {
            val callback = ItemMoveCallback(adapter, viewModel.listContact.value)
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(binding.rcView)
        }
    }

    private fun init() = with(binding) {
        adapter = ContactAdapter(this@ListContactFragment)
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bAdd.setOnClickListener {
            FragmentManager.nextFragment(CreateContactFragment.newInstance(), LIST_CONTACT_FRAGMENT_TAG, requireActivity())
        }
        bDelete.setOnClickListener {
            onClickDelete()
        }
        bCancel.setOnClickListener {
            onClickCancel()
        }
        icDelete.setOnClickListener {
            viewModel.clickOnIconDelete.value = true
            onClickIconDelete()
        }
        icReset.setOnClickListener {
            viewModel.clickOnIconDelete.value = false
            onClickIconReset()
        }
        adapter.submitList(viewModel.listContact.value)
    }

    private fun onClickIconDelete() = with(binding) {
        icDelete.visibility = View.GONE
        icReset.visibility = View.VISIBLE
        bAdd.visibility = View.GONE
        lhCancelOrDelete.visibility = View.VISIBLE
    }

    private fun onClickIconReset() = with(binding) {
        icDelete.visibility = View.VISIBLE
        icReset.visibility = View.GONE
        bAdd.visibility = View.VISIBLE
        lhCancelOrDelete.visibility = View.GONE

        onClickCancel()
    }

    private fun onClickDelete() {
        var listPositions = viewModel.listContact.value?.mapIndexedNotNull { index, contact ->
            if (viewModel.listDeleteContact.value?.contains(contact)!!) index else null
        }
        viewModel.listContact.value?.let { contacts ->
            viewModel.listDeleteContact.value?.let { deletedContacts ->
                contacts.removeAll(deletedContacts)
            }
        }
        listPositions = listPositions?.sortedDescending()
        listPositions?.let{
            listPositions.forEach {
                adapter.notifyItemRemoved(it)
            }
        }
        viewModel.listDeleteContact.value = mutableListOf()
    }

    private fun onClickCancel() {
        val listPositions = viewModel.listContact.value?.mapIndexedNotNull { index, contact ->
            if (contact.isTurn) index else null
        }

        listPositions?.forEach { position ->
            viewModel.listContact.value!![position] = viewModel.listContact.value!![position].copy(isTurn = false)
            adapter.notifyItemChanged(position)
        }
        viewModel.listDeleteContact.value = mutableListOf()
    }

    override fun onStop() {
        super.onStop()
        val layoutManager = binding.rcView.layoutManager as LinearLayoutManager
        viewModel.scrollState.value = layoutManager.findFirstVisibleItemPosition()
    }

    override fun onResume() {
        super.onResume()
        checkStateDeleteMode()
    }

    private fun checkStateDeleteMode() {
        if (viewModel.clickOnIconDelete.value == true){
            onClickIconDelete()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListContactFragment()
    }

    override fun onClick(contact: ContactModel) {
        if (binding.bAdd.visibility == View.VISIBLE) {
            val position = viewModel.listContact.value?.indexOf(contact)
            viewModel.currentContact = contact
            FragmentManager.nextFragment(
                EditContactFragment.newInstance(position!!),
                LIST_CONTACT_FRAGMENT_TAG,
                requireActivity()
            )
        } else {
            val position = viewModel.listContact.value?.indexOf(contact)
            if (position != null && position != -1) {
                if (contact.isTurn){
                    viewModel.listDeleteContact.value?.remove(contact)
                    viewModel.listContact.value!![position] = contact.copy(isTurn = false)
                } else {
                    viewModel.listDeleteContact.value?.add(contact.copy(isTurn = true))
                    viewModel.listContact.value!![position] = contact.copy(isTurn = true)
                }
                adapter.notifyItemChanged(position)
            }
        }
    }

}