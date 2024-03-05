package com.danielp4.recyclerapp.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.danielp4.recyclerapp.R
import com.danielp4.recyclerapp.databinding.ActivityMainBinding
import com.danielp4.recyclerapp.ui.fragments.ListContactFragment
import com.danielp4.recyclerapp.utils.ContactViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel: ContactViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (viewModel.listContact.value==null) {
            viewModel.init()
        }
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.place_holder, ListContactFragment.newInstance())
                .commit()
        }

    }

}