package com.example.contactlist.ui.contact.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.ui.contact.base.BaseContactFragment

class EditContactFragment: BaseContactFragment() {
    private val viewModel: EditContactViewModel by viewModels {
        EditContactViewModel.Provider(ContactRepository.contactRepository)
    }

    val args: EditContactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onViewCreated(args.id)
        binding.btnSave.text = "Update"
        onBindView()
    }

    fun onBindView() {
        viewModel.name.observe(viewLifecycleOwner) {
            binding.etName.setText(it)
        }

        viewModel.phone.observe(viewLifecycleOwner) {
            binding.etPhone.setText(it)
        }

        binding.btnSave.setOnClickListener {
            val contact = Contact(
                name = binding.etName.text.toString(),
                phone = binding.etPhone.text.toString()
            )

            viewModel.update(args.id, contact)
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("edit_contact_finished", bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }
    }
}