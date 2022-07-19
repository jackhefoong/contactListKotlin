package com.example.contactlist.ui.contact.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactFragment

class AddContactFragment : BaseContactFragment() {
    private val viewModel: AddContactViewModel by viewModels {
        AddContactViewModel.Provider(ContactRepository.contactRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.addTextChangedListener {
            viewModel.name.value = it.toString()
        }

        binding.btnSave.addTextChangedListener {
            viewModel.phone.value = it.toString()
        }

        binding.btnSave.setOnClickListener {
            viewModel.save()
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("add_contact_finished", bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}