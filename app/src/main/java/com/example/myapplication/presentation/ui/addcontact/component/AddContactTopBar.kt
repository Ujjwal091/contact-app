package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

/**
 * Top app bar for the add/edit contact screen
 *
 * @param isEditMode Whether the screen is in edit mode
 * @param onBackClick Callback when the back button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactTopBar(
    isEditMode: Boolean = false,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { 
            Text(
                text = if (isEditMode) "Edit Contact" else "Add Contact",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            ) 
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Preview
@Composable
fun AddContactTopBarPreview() {
    MaterialTheme {
        AddContactTopBar(
            isEditMode = false,
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun EditContactTopBarPreview() {
    MaterialTheme {
        AddContactTopBar(
            isEditMode = true,
            onBackClick = {}
        )
    }
}