package com.example.myapplication.presentation.ui.editcontact.component

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Card for editing contact information
 *
 * @param name The current name value
 * @param phone The current phone value
 * @param onNameChange Callback when the name is changed
 * @param onPhoneChange Callback when the phone is changed
 */
@Composable
fun EditContactInfoCard(
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
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
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

            // Name
            EditContactInfoItem(
                icon = Icons.Default.Person,
                label = "Name",
                value = name,
                onValueChange = onNameChange
            )

            // Phone
            EditContactInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = phone,
                onValueChange = onPhoneChange,
                keyboardType = KeyboardType.Phone,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditContactInfoCardPreview() {
    EditContactInfoCard(
        name = "John Doe",
        phone = "+1 123 456 7890",
        onNameChange = {},
        onPhoneChange = {}
    )
}
