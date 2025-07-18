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
 * Form for adding or editing a contact
 *
 * @param name The current name value
 * @param phone The current phone value
 * @param email The current email value
 * @param company The current company value
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 * @param onEmailChange Callback when the email is changed
 * @param onCompanyChange Callback when the company is changed
 * @param onSaveClick Callback when the save button is clicked
 * @param isLoading Whether the form is in a loading state
 * @param isEditMode Whether the form is in edit mode
 * @param nameError Error message for the name field, null if no error
 * @param phoneError Error message for the phone field, null if no error
 * @param emailError Error message for the email field, null if no error
 * @param companyError Error message for the company field, null if no error
 */
@Composable
fun AddContactForm(
    name: String,
    phone: String,
    email: String,
    company: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCompanyChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    isLoading: Boolean = false,
    isEditMode: Boolean = false,
    nameError: String? = null,
    phoneError: String? = null,
    emailError: String? = null,
    companyError: String? = null
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
            email = email,
            company = company,
            onNameChange = onNameChange,
            onPhoneChange = onPhoneChange,
            onEmailChange = onEmailChange,
            onCompanyChange = onCompanyChange,
            nameError = nameError,
            phoneError = phoneError,
            emailError = emailError,
            companyError = companyError
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
            Text(if (isEditMode) "Update Contact" else "Save Contact")
        }
    }
}

/**
 * Data class representing form parameters for preview
 */
data class AddContactFormParams(
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val companyError: String? = null
)

/**
 * Preview parameter provider for AddContactForm
 */
class AddContactFormParamsProvider : PreviewParameterProvider<AddContactFormParams> {
    override val values = sequenceOf(
        AddContactFormParams(
            name = "John Doe",
            phone = "+1 123 456 7890",
            email = "john.doe@example.com",
            company = "Example Corp"
        ),
        AddContactFormParams(
            name = "",
            phone = "",
            email = "",
            company = ""
        ),
        AddContactFormParams(
            name = "Jane Smith",
            phone = "+91 9876543210",
            email = "jane.smith@company.com",
            company = "Tech Solutions",
            isLoading = true
        ),
        AddContactFormParams(
            name = "Bob Wilson",
            phone = "+1 555 0123",
            email = "bob.wilson@example.com",
            company = "Example Inc",
            isEditMode = true
        ),
        AddContactFormParams(
            name = "",
            phone = "",
            email = "",
            company = "",
            nameError = "Name cannot be empty",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactFormParams(
            name = "",
            phone = "+1 555 0123",
            email = "",
            company = "",
            nameError = "Name cannot be empty"
        ),
        AddContactFormParams(
            name = "Bob Wilson",
            phone = "",
            email = "",
            company = "",
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
            email = params.email,
            company = params.company,
            onNameChange = {},
            onPhoneChange = {},
            onEmailChange = {},
            onCompanyChange = {},
            onSaveClick = {},
            isLoading = params.isLoading,
            isEditMode = params.isEditMode,
            nameError = params.nameError,
            phoneError = params.phoneError,
            emailError = params.emailError,
            companyError = params.companyError
        )
    }
}
