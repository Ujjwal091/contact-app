package com.example.myapplication.presentation.ui.contactdetail.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


/**
 * Top app bar for the contact detail screen
 *
 * @param title The title to display
 * @param onBackClick Callback when the back button is clicked
 * @param onEditClick Callback when the edit button is clicked
 * @param onDeleteClick Callback when the delete button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailTopBar(
    title: String = "Contact Details",
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Contact"
                )
            }
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Contact"
                )
            }
        },
    )
}


// Preview functions
@Preview(showBackground = true)
@Composable
fun ContactDetailTopBarPreview() {
    MaterialTheme {
        ContactDetailTopBar(
            onBackClick = {},
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}
