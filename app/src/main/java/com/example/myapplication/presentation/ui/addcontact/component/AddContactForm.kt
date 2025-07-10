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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
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
    isLoading: Boolean = false,
    nameError: String? = null,
    phoneError: String? = null
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
            onPhoneChange = onPhoneChange,
            nameError = nameError,
            phoneError = phoneError
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

/**
 * Data class representing form parameters for preview
 */
data class AddContactFormParams(
    val name: String,
    val phone: String,
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val phoneError: String? = null
)

/**
 * Preview parameter provider for AddContactForm
 */
class AddContactFormParamsProvider : PreviewParameterProvider<AddContactFormParams> {
    override val values = sequenceOf(
        AddContactFormParams(
            name = "John Doe",
            phone = "+1 123 456 7890"
        ),
        AddContactFormParams(
            name = "",
            phone = ""
        ),
        AddContactFormParams(
            name = "Jane Smith",
            phone = "+91 9876543210",
            isLoading = true
        ),
        AddContactFormParams(
            name = "",
            phone = "",
            nameError = "Name cannot be empty",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactFormParams(
            name = "",
            phone = "+1 555 0123",
            nameError = "Name cannot be empty"
        ),
        AddContactFormParams(
            name = "Bob Wilson",
            phone = "",
            phoneError = "Phone number cannot be empty"
        )
    )
}

/**
 * Preview of AddContactForm with different parameter combinations
 */
@Preview(showBackground = true, group = "Add Contact Form States")
@Composable
fun AddContactFormPreview(
    @PreviewParameter(AddContactFormParamsProvider::class) params: AddContactFormParams
) {
    MaterialTheme {
        AddContactForm(
            name = params.name,
            phone = params.phone,
            onNameChange = {},
            onPhoneChange = {},
            onSaveClick = {},
            isLoading = params.isLoading,
            nameError = params.nameError,
            phoneError = params.phoneError
        )
    }
}
