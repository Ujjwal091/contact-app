package com.example.myapplication.presentation.ui.contactlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.component.getFirstValidCharacter


/**
 * A composable function that displays a single contact item in the list.
 * Extracted to a separate function to improve performance and reusability.
 */
@Composable
private fun ContactListItem(
    contact: Contact,
    onClick: (Contact) -> Unit,
    modifier: Modifier = Modifier
) {
    // Use remember to avoid recreating the click handler on each recomposition
    val onClickHandler = remember(contact.id) { { onClick(contact) } }

    // Pre-compute the avatar letter
    val avatarLetter = remember(contact.name) { getFirstValidCharacter(contact.name) }

    Surface(
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large) // Apply clip before clickable for better touch feedback
            .clickable(onClick = onClickHandler)
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Avatar box
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Use the pre-computed avatar letter
                Text(
                    text = avatarLetter,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Contact details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * A composable function that displays a list of contacts.
 * Optimized for performance with proper content padding and item spacing.
 */
@Composable
fun ContactList(contacts: List<Contact>, onClick: (Contact) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (contacts.isEmpty()) {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contacts found",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = MaterialTheme.typography.bodyLarge.fontSize * 1.5f,
                    letterSpacing = MaterialTheme.typography.bodyLarge.letterSpacing * 1.5f,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize * 1.5f,
                )
            }
        } else {
            // Contact list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(
                    items = contacts,
                    key = { it.id } // Stable key for better performance
                ) { contact ->
                    ContactListItem(
                        contact = contact,
                        onClick = onClick
                    )
                }
            }
        }
    }
}