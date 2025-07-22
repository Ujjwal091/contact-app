package com.example.myapplication.presentation.ui.contactlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.domain.usecase.GetAllContactsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel for the contact list screen.
 * Includes performance monitoring and optimizations for better UI performance.
 */
class ContactListViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "ContactListViewModel"
    }

    private val _state = MutableStateFlow<ContactListState>(ContactListState.Loading)
    val state: StateFlow<ContactListState> = _state

    // Cache the contacts to avoid unnecessary fetches
    private var cachedContacts: List<Contact>? = null

    // Separate flow for search query to enable debouncing
    private val _searchQuery = MutableStateFlow("")

    // Performance metrics
    private var lastLoadTime = 0L
    private var lastFilterTime = 0L

    init {
        // Set up debounced search query handling
        setupSearchQueryHandling()
        Log.d(TAG, "ViewModel initialized")
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchQueryHandling() {
        // Apply debounce to avoid processing every keystroke
        // Only process search query changes after 300ms of inactivity
        _searchQuery
            .debounce(300)
            .distinctUntilChanged()
            .onEach { query ->
                Log.d(TAG, "Processing search query: '$query'")
                val startTime = System.currentTimeMillis()
                updateStateWithQuery(query)
                val endTime = System.currentTimeMillis()
                lastFilterTime = endTime - startTime
                Log.d(TAG, "Search filtering completed in ${lastFilterTime}ms")
            }
            .launchIn(viewModelScope)

        Log.d(TAG, "Search query handling set up with 300ms debounce")
    }

    private fun updateStateWithQuery(query: String) {
        val currentState = _state.value
        if (currentState is ContactListState.Success) {
            // Create a new state with the updated query and pre-computed filtered contacts
            _state.value = ContactListState.Success(
                contacts = currentState.contacts,
                searchQuery = query
            )
        }
    }

    fun loadContacts() {
        viewModelScope.launch {
            try {
                val startTime = System.currentTimeMillis()
                Log.d(TAG, "Loading contacts started")

                // Only show loading state if we don't have cached contacts
                if (cachedContacts == null) {
                    _state.value = ContactListState.Loading
                    Log.d(TAG, "Showing loading state (no cached contacts)")
                } else {
                    Log.d(TAG, "Using cached contacts (${cachedContacts?.size ?: 0} items)")
                }

                // Fetch contacts
                val fetchStartTime = System.currentTimeMillis()
                val contacts = getAllContactsUseCase()
                val fetchEndTime = System.currentTimeMillis()
                Log.d(TAG, "Fetched ${contacts.size} contacts in ${fetchEndTime - fetchStartTime}ms")

                // Cache the contacts
                cachedContacts = contacts

                // Update state with current search query
                val query = _searchQuery.value
                val updateStartTime = System.currentTimeMillis()
                _state.value = ContactListState.Success(
                    contacts = contacts,
                    searchQuery = query
                )
                val updateEndTime = System.currentTimeMillis()

                val endTime = System.currentTimeMillis()
                lastLoadTime = endTime - startTime

                Log.d(TAG, "Contacts loading completed in ${lastLoadTime}ms")
                Log.d(TAG, "  - Fetch time: ${fetchEndTime - fetchStartTime}ms")
                Log.d(TAG, "  - State update time: ${updateEndTime - updateStartTime}ms")
                if (query.isNotBlank()) {
                    Log.d(TAG, "  - Initial filtering for query '$query'")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error loading contacts", e)
                _state.value = ContactListState.Error("Failed to load contacts")
            }
        }
    }

    fun updateSearchQuery(query: String) {
        Log.d(TAG, "Search query updated: '$query'")
        // Update the search query flow, which will trigger the debounced handler
        _searchQuery.value = query
    }

    /**
     * Returns performance metrics for debugging and monitoring.
     */
    fun getPerformanceMetrics(): Map<String, Long> {
        return mapOf(
            "lastLoadTime" to lastLoadTime,
            "lastFilterTime" to lastFilterTime,
            "cachedContactsCount" to (cachedContacts?.size?.toLong() ?: 0)
        )
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel cleared, final performance metrics: ${getPerformanceMetrics()}")
    }
}