package com.danielp4.recyclerapp.utils

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielp4.recyclerapp.R
import com.danielp4.recyclerapp.data.ContactModel

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    val listContact = MutableLiveData<MutableList<ContactModel>>()
    val listDeleteContact = MutableLiveData<MutableList<ContactModel>>()

    var currentContact: ContactModel? = null

    val scrollState = MutableLiveData<Int>()

    val clickOnIconDelete = MutableLiveData<Boolean>()

    fun init() {
        listContact.value = generateContact()
        listDeleteContact.value = mutableListOf()
    }

    private fun generateContact(): MutableList<ContactModel> {
        val contacts = mutableListOf<ContactModel>()
        for (i in 1..100) {
            val contactModel = ContactModel(
                id = i,
                firstName = getApplication<Application>().resources.getStringArray(R.array.list_first_name).random(),
                secondName = getApplication<Application>().resources.getStringArray(R.array.list_second_name).random(),
                phoneNumber = getApplication<Application>().resources.getStringArray(R.array.list_phone_number).random(),
                isTurn = false
            )
            contacts.add(contactModel)
        }
        return contacts
    }

}