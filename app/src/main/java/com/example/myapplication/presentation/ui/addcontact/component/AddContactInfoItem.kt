package com.example.myapplication.presentation.ui.addcontact.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp

/**
 * Item for adding a piece of contact information
 *
 * @param icon The icon to display
 * @param label The label for the information
 * @param value The current value of the information
 * @param onValueChange Callback when the value is changed
 * @param keyboardType The keyboard type to use for the text field
 * @param imeAction The IME action to use for the text field
 */
@Composable
fun AddContactInfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    errorMessage: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                isError = errorMessage != null
            )
        }

        // Show error message if present
        if (errorMessage != null) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(40.dp)) // Align with text field
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

/**
 * Data class representing AddContactInfoItem parameters for preview
 */
data class AddContactInfoItemParams(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val imeAction: ImeAction = ImeAction.Next,
    val errorMessage: String? = null
)

/**
 * Preview parameter provider for AddContactInfoItem
 */
class AddContactInfoItemParamsProvider : PreviewParameterProvider<AddContactInfoItemParams> {
    override val values = sequenceOf(
        AddContactInfoItemParams(
            icon = Icons.Default.Person,
            label = "Name",
            value = "John Doe",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        AddContactInfoItemParams(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = "+91 8957695482",
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        AddContactInfoItemParams(
            icon = Icons.Default.Person,
            label = "Name",
            value = "",
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            errorMessage = "Name cannot be empty"
        ),
        AddContactInfoItemParams(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = "",
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done,
            errorMessage = "Phone number cannot be empty"
        )
    )
}

/**
 * Preview of AddContactInfoItem with different parameter combinations
 */
@Preview(showBackground = true, group = "Add Contact Info Item States")
@Composable
fun AddContactInfoItemPreview(
    @PreviewParameter(AddContactInfoItemParamsProvider::class) params: AddContactInfoItemParams
) {
    MaterialTheme {
        AddContactInfoItem(
            icon = params.icon,
            label = params.label,
            value = params.value,
            onValueChange = {},
            keyboardType = params.keyboardType,
            imeAction = params.imeAction,
            errorMessage = params.errorMessage
        )
    }
}