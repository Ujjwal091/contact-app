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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
                // General error state is handled by the parent component with a dialog
                // But we still show the form so user can retry
                AddContactForm(
                    name = name,
                    phone = phone,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onSaveClick = { onAddContact(name, phone) }
                )
            }

            is AddContactState.Success -> {
                // Success state is handled by the parent component
                // But we still show the form briefly before navigation
                AddContactForm(
                    name = name,
                    phone = phone,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onSaveClick = { onAddContact(name, phone) }
                )
            }

            is AddContactState.FormError -> {
                // Show form with field-specific errors
                AddContactForm(
                    name = name,
                    phone = phone,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onSaveClick = { onAddContact(name, phone) },
                    nameError = state.nameError,
                    phoneError = state.phoneError
                )
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

/**
 * Preview parameter provider for AddContactState
 */
class AddContactContentStateProvider : PreviewParameterProvider<AddContactState> {
    override val values = sequenceOf(
        AddContactState.Initial,
        AddContactState.Loading,
        AddContactState.Success,
        AddContactState.Error("Failed to add contact"),
        AddContactState.FormError(
            nameError = "Name cannot be empty",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactState.FormError(
            nameError = "Name cannot be empty",
            phoneError = null
        ),
        AddContactState.FormError(
            nameError = null,
            phoneError = "Phone number cannot be empty"
        )
    )
}

/**
 * Preview of AddContactContent with different states
 */
@Preview(showBackground = true, group = "Add Contact Content States")
@Composable
fun AddContactContentPreview(
    @PreviewParameter(AddContactContentStateProvider::class) state: AddContactState
) {
    MaterialTheme {
        AddContactContent(
            state = state,
            onAddContact = { _, _ -> },
            onBackClick = {}
        )
    }
}
