package com.danielp4.recyclerapp.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.danielp4.recyclerapp.R
import com.danielp4.recyclerapp.utils.ContactViewModel
import com.danielp4.recyclerapp.data.ContactModel
import com.danielp4.recyclerapp.databinding.FragmentEditContactBinding
import com.danielp4.recyclerapp.utils.Constants.POSITION_KEY
import com.danielp4.recyclerapp.utils.FragmentManager

class EditContactFragment : Fragment() {

    private lateinit var binding: FragmentEditContactBinding
    private val viewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() = with(binding) {
        edFirstName.text = Editable.Factory.getInstance().newEditable(viewModel.currentContact?.firstName!!)
        edSecondName.text = Editable.Factory.getInstance().newEditable(viewModel.currentContact?.secondName!!)
        edPhoneNumber.text = Editable.Factory.getInstance().newEditable(viewModel.currentContact?.phoneNumber!!)
        bSave.setOnClickListener {
            val idCurrentContact = viewModel.currentContact?.id

            if (edFirstName.text.toString().isEmpty() || edSecondName.text.toString().isEmpty() || edPhoneNumber.text.toString().isEmpty()) {
                Toast.makeText(activity as AppCompatActivity, getString(R.string.warning), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val contact = ContactModel(
                id = idCurrentContact!!,
                firstName = edFirstName.text.toString(),
                secondName = edSecondName.text.toString(),
                phoneNumber = edPhoneNumber.text.toString(),
                isTurn = false
            )

            val position = requireArguments().getInt(POSITION_KEY)
            viewModel.listContact.value!![position] = contact
            FragmentManager.previousFragment(requireActivity())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(position: Int): EditContactFragment {
            return EditContactFragment().apply {
                arguments = Bundle().also { it.putInt(POSITION_KEY, position) }
            }
        }
    }
}