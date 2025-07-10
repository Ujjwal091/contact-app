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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
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
    onPhoneChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
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
                imeAction = ImeAction.Next
            )

            // Phone field
            AddContactInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = phone,
                onValueChange = onPhoneChange,
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            )
        }
    }
}

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
    imeAction: ImeAction = ImeAction.Next
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
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
            shape = MaterialTheme.shapes.small
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddContactInfoCardPreview() {
    MaterialTheme {
        AddContactInfoCard(
            name = "John Doe",
            phone = "+1 123 456 7890",
            onNameChange = {},
            onPhoneChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddContactInfoItemPreview() {
    MaterialTheme {
        AddContactInfoItem(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = "+91 8957695482",
            onValueChange = {}
        )
    }
}
