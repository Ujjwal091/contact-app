package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.ui.addcontact.AddContactState

/**
 * Content for the add contact screen
 *
 * @param state The state of the add contact screen
 * @param onAddContact Callback when a contact is added with name and phone
 * @param onBackClick Callback when the back button is clicked
 */
@Composable
fun AddContactContent(
    state: AddContactState,
    onAddContact: (name: String, phone: String) -> Unit,
    onBackClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (state) {
            is AddContactState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 4.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            is AddContactState.Error -> {
                // Error state is handled by the parent component with a dialog
            }

            is AddContactState.Success -> {
                // Success state is handled by the parent component
            }

            is AddContactState.Initial -> {
                AddContactForm(
                    name = name,
                    phone = phone,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onSaveClick = { onAddContact(name, phone) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddContactContentPreview() {
    MaterialTheme {
        AddContactContent(
            state = AddContactState.Initial,
            onAddContact = { _, _ -> },
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun AddContactContentLoadingPreview() {
    MaterialTheme {
        AddContactContent(
            state = AddContactState.Loading,
            onAddContact = { _, _ -> },
            onBackClick = {}
        )
    }
}