package com.example.myapplication.presentation.ui.contactlist.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactlist.ContactListState

/**
 * A placeholder item that simulates a contact list item during loading.
 */
@Composable
private fun ContactPlaceholderItem(modifier: Modifier = Modifier) {
    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        shape = MaterialTheme.shapes.medium
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Content placeholders
            Column(modifier = Modifier.weight(1f)) {
                // Name placeholder
                Box(
                    modifier = Modifier
                        .height(18.dp)
                        .fillMaxWidth(0.7f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Phone placeholder
                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))
                )
            }
        }
    }
}

/**
 * A loading placeholder that displays shimmer-like placeholders for contact items.
 */
@Composable
private fun LoadingPlaceholder() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        // Display 10 placeholder items
        items(10) {
            ContactPlaceholderItem()
        }
    }
}

/**
 * Main content component for the contact list screen.
 * Handles different states (Loading, Error, Success) and displays appropriate UI.
 */
@Composable
fun ContactListContent(
    state: ContactListState,
    onContactClick: (Contact) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state) {
            is ContactListState.Loading -> {
                // Show loading placeholder with animation
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(300)),
                    exit = fadeOut(animationSpec = tween(300))
                ) {
                    LoadingPlaceholder()
                }

                // Also show a progress indicator for better UX
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp),
                    strokeWidth = 4.dp
                )
            }

            is ContactListState.Error -> Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    state.message,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
            }

            is ContactListState.Success -> ContactList(
                contacts = state.filteredContacts,
                onClick = onContactClick
            )
        }
    }
}