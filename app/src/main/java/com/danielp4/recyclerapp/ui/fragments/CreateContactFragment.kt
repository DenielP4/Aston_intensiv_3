package com.danielp4.recyclerapp.ui.fragments

import android.os.Bundle
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
import com.danielp4.recyclerapp.databinding.FragmentCreateContactBinding
import com.danielp4.recyclerapp.utils.FragmentManager
import kotlin.random.Random

class CreateContactFragment : Fragment() {

    private lateinit var binding: FragmentCreateContactBinding
    private val viewModel: ContactViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() = with(binding) {
        bCreate.setOnClickListener {
            if (edFirstName.text.toString().isEmpty() || edSecondName.text.toString().isEmpty() || edPhoneNumber.text.toString().isEmpty()) {
                Toast.makeText(activity as AppCompatActivity, getString(R.string.warning), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val id = Random.nextInt(viewModel.listContact.value?.size!!+1, 1000)
            val contact = ContactModel(
                id = id,
                firstName = edFirstName.text.toString(),
                secondName = edSecondName.text.toString(),
                phoneNumber = edPhoneNumber.text.toString(),
                isTurn = false
            )
            viewModel.listContact.value?.add(contact)
            FragmentManager.previousFragment(requireActivity())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CreateContactFragment()
    }
}