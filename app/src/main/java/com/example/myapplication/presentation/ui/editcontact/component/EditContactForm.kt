package com.example.myapplication.presentation.ui.editcontact.component

import androidx.compose.foundation.layout.*
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
 * Form for editing a contact
 *
 * @param name The current name value
 * @param phone The current phone value
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 * @param onUpdateClick Callback when the update button is clicked
 */
@Composable
fun EditContactForm(
    name: String,
    phone: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onUpdateClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactAvatar(name = name)

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Contact Information Card
        EditContactInfoCard(
            name = name,
            phone = phone,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Update button
        Button(
            shape = MaterialTheme.shapes.medium,
            onClick = onUpdateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Update Contact")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditContactFormPreview() {
    MaterialTheme {
        EditContactForm(
            name = "John Doe",
            phone = "+1 123 456 7890",
            onNameChange = {},
            onPhoneChange = {},
            onUpdateClick = {}
        )
    }
}
