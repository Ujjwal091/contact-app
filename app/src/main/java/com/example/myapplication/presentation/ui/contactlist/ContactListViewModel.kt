package com.example.myapplication.presentation.ui.contactlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.GetAllContactsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactListViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ContactListState>(ContactListState.Loading)
    val state: StateFlow<ContactListState> = _state

    fun loadContacts() {
        viewModelScope.launch {
            try {
                _state.value = ContactListState.Loading
                val contacts = getAllContactsUseCase()
                _state.value = ContactListState.Success(contacts)
            } catch (_: Exception) {
                _state.value = ContactListState.Error("Failed to load contacts")
            }
        }
    }
}