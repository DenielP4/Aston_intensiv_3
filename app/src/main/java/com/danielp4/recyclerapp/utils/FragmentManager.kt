package com.danielp4.recyclerapp.utils

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.danielp4.recyclerapp.R

object FragmentManager {

    fun nextFragment(nextFragment: Fragment, tag: String, requireActivity: FragmentActivity) {
        val transaction = requireActivity.supportFragmentManager.beginTransaction()
        transaction
            .replace(R.id.place_holder, nextFragment)
            .addToBackStack(tag)
            .commit()
    }

    fun previousFragment(requireActivity: FragmentActivity) {
        requireActivity.supportFragmentManager.popBackStack()
    }
}