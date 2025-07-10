package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 */
@Composable
fun AddContactInfoCard(
    name: String,
    phone: String,
    onNameChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    nameError: String? = null,
    phoneError: String? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            ,
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
                imeAction = ImeAction.Done,
                errorMessage = phoneError
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
    val nameError: String? = null,
    val phoneError: String? = null
)

/**
 * Preview parameter provider for AddContactInfoCard
 */
class AddContactInfoCardParamsProvider : PreviewParameterProvider<AddContactInfoCardParams> {
    override val values = sequenceOf(
        AddContactInfoCardParams(
            name = "John Doe",
            phone = "+1 123 456 7890"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = ""
        ),
        AddContactInfoCardParams(
            name = "Jane Smith",
            phone = "+91 9876543210"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = "",
            nameError = "Name cannot be empty",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "",
            phone = "+1 555 0123",
            nameError = "Name cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "Bob Wilson",
            phone = "",
            phoneError = "Phone number cannot be empty"
        ),
        AddContactInfoCardParams(
            name = "Alice Johnson",
            phone = "invalid123abc",
            phoneError = "Phone number contains invalid characters"
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
            onNameChange = {},
            onPhoneChange = {},
            nameError = params.nameError,
            phoneError = params.phoneError
        )
    }
}
