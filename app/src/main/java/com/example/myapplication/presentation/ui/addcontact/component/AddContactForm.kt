package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.component.ContactAvatar

/**
 * Form for adding a new contact
 *
 * @param name The current name value
 * @param phone The current phone value
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 * @param onSaveClick Callback when the save button is clicked
 * @param isLoading Whether the form is in a loading state
 */
@Composable
fun AddContactForm(
    name: String,
    phone: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isLoading: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contact Avatar
        ContactAvatar(name = name.ifEmpty { "?" })

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Information Card
        AddContactInfoCard(
            name = name,
            phone = phone,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save button
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = MaterialTheme.shapes.medium,
            enabled = !isLoading
        ) {
            Text("Save Contact")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddContactFormPreview() {
    MaterialTheme {
        AddContactForm(
            name = "John Doe",
            phone = "+1 123 456 7890",
            onNameChange = {},
            onPhoneChange = {},
            onSaveClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Empty Form")
@Composable
fun AddContactFormEmptyPreview() {
    MaterialTheme {
        AddContactForm(
            name = "",
            phone = "",
            onNameChange = {},
            onPhoneChange = {},
            onSaveClick = {}
        )
    }
}