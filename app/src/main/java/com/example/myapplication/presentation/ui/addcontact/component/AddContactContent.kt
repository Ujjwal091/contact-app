package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.addcontact.AddContactState

/**
 * Content for the add/edit contact screen
 *
 * @param state The state of the add/edit contact screen
 * @param isEditMode Whether the screen is in edit mode
 * @param onSaveContact Callback when a contact is saved with name, phone, email, and company
 * @param onBackClick Callback when the back button is clicked
 */
@Composable
fun AddContactContent(
    state: AddContactState,
    isEditMode: Boolean = false,
    onSaveContact: (name: String, phone: String, email: String, company: String) -> Unit,
    onBackClick: () -> Unit,
    onHasUnsavedChanges: (Boolean) -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var company by remember { mutableStateOf("") }

    // Store original values to detect changes
    var originalName by remember { mutableStateOf("") }
    var originalPhone by remember { mutableStateOf("") }
    var originalEmail by remember { mutableStateOf("") }
    var originalCompany by remember { mutableStateOf("") }

    // Function to check if there are unsaved changes
    val hasUnsavedChanges = remember {
        {
            name != originalName ||
                    phone != originalPhone ||
                    email != originalEmail ||
                    company != originalCompany
        }
    }

    // Notify parent about unsaved changes whenever form values change
    LaunchedEffect(name, phone, email, company) {
        onHasUnsavedChanges(hasUnsavedChanges())
    }

    // If in edit mode and we have contact data, prefill the form
    LaunchedEffect(state) {
        if (state is AddContactState.Editing) {
            val contactName = state.contact.name
            val contactPhone = state.contact.phone
            val contactEmail = state.contact.email ?: ""
            val contactCompany = state.contact.company ?: ""

            // Set current values
            name = contactName
            phone = contactPhone
            email = contactEmail
            company = contactCompany

            // Set original values
            originalName = contactName
            originalPhone = contactPhone
            originalEmail = contactEmail
            originalCompany = contactCompany
        } else if (state is AddContactState.Initial) {
            // For new contacts, set original values to empty
            originalName = ""
            originalPhone = ""
            originalEmail = ""
            originalCompany = ""
        }
    }

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
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    isEditMode = isEditMode
                )
            }

            is AddContactState.Success -> {
                // Success state is handled by the parent component
                // But we still show the form briefly before navigation
                AddContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    isEditMode = isEditMode
                )
            }

            is AddContactState.FormError -> {
                // Show form with field-specific errors
                AddContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    nameError = state.nameError,
                    phoneError = state.phoneError,
                    emailError = state.emailError,
                    companyError = state.companyError,
                    isEditMode = isEditMode
                )
            }

            is AddContactState.Initial -> {
                AddContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    isEditMode = isEditMode
                )
            }

            is AddContactState.Editing -> {
                AddContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    isEditMode = isEditMode
                )
            }

            is AddContactState.NotFound -> {
                // Not found state is handled by the parent component with a dialog
                // But we still show an empty form
                AddContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onSaveClick = { onSaveContact(name, phone, email, company) },
                    isEditMode = isEditMode
                )
            }
        }
    }
}

/**
 * For backward compatibility with existing code
 */
@Composable
fun AddContactContent(
    state: AddContactState,
    onAddContact: (name: String, phone: String, email: String, company: String) -> Unit,
    onBackClick: () -> Unit,
    onHasUnsavedChanges: (Boolean) -> Unit = {}
) {
    AddContactContent(
        state = state,
        isEditMode = false,
        onSaveContact = onAddContact,
        onBackClick = onBackClick,
        onHasUnsavedChanges = onHasUnsavedChanges
    )
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
        AddContactState.Editing(
            Contact(
                id = "1",
                name = "John Doe",
                phone = "+1 123 456 7890",
                email = "john.doe@example.com",
                company = "Example Corp"
            )
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
            isEditMode = state is AddContactState.Editing,
            onSaveContact = { _, _, _, _ -> },
            onBackClick = {}
        )
    }
}
