package com.danielp4.recyclerapp.data

import java.io.Serializable

data class ContactModel(
    val id: Int,
    val firstName: String,
    val secondName: String,
    val phoneNumber: String,
    val isTurn: Boolean
) : Serializable
