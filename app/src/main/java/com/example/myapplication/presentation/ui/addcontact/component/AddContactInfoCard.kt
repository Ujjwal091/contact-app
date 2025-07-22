package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Card for adding contact information
 *
 * @param name The current name value
 * @param phone The current phone value
 * @param email The current email value
 * @param company The current company value
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 * @param onEmailChange Callback when the email is changed
 * @param onCompanyChange Callback when the company is changed
 */
@Composable
fun AddContactInfoCard(
    name: String,
    phone: String,
    email: String,
    company: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCompanyChange: (String) -> Unit,
    nameError: String? = null,
    phoneError: String? = null,
    emailError: String? = null,
    companyError: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),

        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Contact Information",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Name field
            AddContactInfoItem(
                icon = Icons.Default.Person,
                label = "Name",
                value = name,
                onValueChange = onNameChange,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
                errorMessage = nameError
            )

            // Phone field
            AddContactInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = phone,
                onValueChange = { newValue ->
                    // Filter to allow only digits, spaces, hyphens, parentheses, and plus sign
                    val filteredValue = newValue.filter { char ->
                        char.isDigit() || char in " -+"
                    }
                    onPhoneChange(filteredValue)
                },
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
                errorMessage = phoneError
            )

            // Email field
            AddContactInfoItem(
                icon = Icons.Default.Email,
                label = "Email",
                value = email,
                onValueChange = onEmailChange,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                errorMessage = emailError
            )

            // Company field
            AddContactInfoItem(
                icon = Icons.Default.Person,
                label = "Company",
                value = company,
                onValueChange = onCompanyChange,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
                errorMessage = companyError
            )
        }
    }
}


/**
 * Data class representing AddContactInfoCard parameters for preview
 */
data class AddContactInfoCardParams(
    val name: String,
    val phone: String,
    val email: String,
    val company: String,
    val nameError: String? = null,
    val phoneError: String? = null,
    val emailError: String? = null,
    val companyError: String? = null
)

/**
 * Preview parameter provider for AddContactInfoCard
 */
class AddContactInfoCardParamsProvider : PreviewParameterProvider<AddContactInfoCardParams> {
    override val values = sequenceOf(
        AddContactInfoCardParams(
            name = "John Doe",
            phone = "+1 123 456 7890",
            email = "john.doe@example.com",
            company = "Example Corp"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = "",
            email = "",
            company = ""
        ),
        AddContactInfoCardParams(
            name = "Jane Smith",
            phone = "+91 9876543210",
            email = "jane.smith@company.com",
            company = "Tech Solutions"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = "",
            email = "",
            company = "",
            nameError = "Name cannot be empty",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = "+1 555 0123",
            email = "",
            company = "",
            nameError = "Name cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "Bob Wilson",
            phone = "",
            email = "",
            company = "",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "Alice Johnson",
            phone = "invalid123abc",
            email = "invalid-email",
            company = "",
            phoneError = "Phone number contains invalid characters",
            emailError = "Please enter a valid email address"
        )
    )
}


/**
 * Preview of AddContactInfoCard with different parameter combinations
 */
@Preview(showBackground = true, group = "Add Contact Info Card States")
@Composable
fun AddContactInfoCardPreview(
    @PreviewParameter(AddContactInfoCardParamsProvider::class) params: AddContactInfoCardParams
) {
    MaterialTheme {
        AddContactInfoCard(
            name = params.name,
            phone = params.phone,
            email = params.email,
            company = params.company,
            onNameChange = {},
            onPhoneChange = {},
            onEmailChange = {},
            onCompanyChange = {},
            nameError = params.nameError,
            phoneError = params.phoneError,
            emailError = params.emailError,
            companyError = params.companyError
        )
    }
}
