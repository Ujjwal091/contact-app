package com.example.myapplication.presentation.ui.editcontact.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Top app bar for the edit contact screen
 *
 * @param onBackClick Callback when the back button is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditContactTopBar(
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = { Text("Edit Contact") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Preview
@Composable
fun EditContactTopBarPreview() {
    EditContactTopBar(
        onBackClick = {}
    )
}