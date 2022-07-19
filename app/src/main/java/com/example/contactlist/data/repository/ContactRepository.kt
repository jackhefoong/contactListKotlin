package com.example.contactlist.data.repository

import com.example.contactlist.data.model.Contact

class ContactRepository {
    var counter = 1
    private val contacts: MutableMap<Int, Contact> = mutableMapOf(
        0 to Contact(name = "hello", phone = "1234"),
        1 to Contact(name = "World!", phone = "5678")
    )

    fun getContacts(): List<Contact> {
        return contacts.values.toList()
    }

    fun addContact(contact: Contact): Contact? {
        counter++
        contacts[counter] = contact.copy(id = counter)
        return contacts[counter]
    }

    fun updateContact(id: Int, contact: Contact): Contact? {
        contacts[id] = contact
        return contacts[id]
    }

    fun findContactById(id: Int) : Contact? {
        return contacts[id]
    }

    companion object{
        val contactRepository = ContactRepository()
    }
}