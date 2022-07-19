package com.example.contactlist.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactlist.data.model.Contact
import com.example.contactlist.data.repository.ContactRepository
import com.example.contactlist.databinding.FragmentHomeBinding
import com.example.contactlist.ui.ContactAdapter


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Provider(ContactRepository.contactRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindView()
        setupFragmentListener()
    }

    fun onBindView() {
        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            setupAdapter(contacts)
        }

        binding.fabAdd.setOnClickListener {
            navigateToAddContact()
        }
    }

    fun setupAdapter(contacts: List<Contact>) {
        val contactAdapter = ContactAdapter(contacts)
        contactAdapter.listener = object: ContactAdapter.Listener {
            override fun onItemClicked(item: Contact) {
                navigateToEditContact(item.id!!)
            }
        }
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvContacts.adapter = contactAdapter
        binding.rvContacts.layoutManager = layoutManager
    }

    fun navigateToAddContact() {
        val action = HomeFragmentDirections.actionHomeToAddContact()
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun navigateToEditContact(id: Int) {
        val action = HomeFragmentDirections.actionHomeToEditContact(id)
        NavHostFragment.findNavController(this).navigate(action)
    }

    fun setupFragmentListener() {
        setFragmentResultListener("add_contact_finished") { _, result ->
            if(result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }

        setFragmentResultListener("edit_contact_finished") { _, result ->
            if(result.getBoolean("refresh")) {
                viewModel.refresh()
            }
        }
    }
}

