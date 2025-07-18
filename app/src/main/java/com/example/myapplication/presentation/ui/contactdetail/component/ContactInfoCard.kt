package com.example.myapplication.presentation.ui.contactdetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.entity.Contact


/**
 * Card displaying contact information
 *
 * @param contact The contact to display information for
 */
@Composable
fun ContactInfoCard(contact: Contact) {
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

            // Phone
            ContactInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = contact.phone,
                isPhone = true
            )

            // Email
            contact.email?.let { email ->
                ContactInfoItem(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = email
                )
            }

            // Address
            contact.address?.let { address ->
                ContactInfoItem(
                    icon = Icons.Default.LocationOn,
                    label = "Address",
                    value = address
                )
            }

            // Company
            contact.company?.let { company ->
                ContactInfoItem(
                    icon = Icons.Default.Person,
                    label = "Company",
                    value = company
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactInfoCardPreview() {
    ContactInfoCard(
        contact = Contact(
            id = "1",
            name = "John Doe",
            phone = "+91 8957695482",
            email = "john.doe@example.com",
            address = "123 Main St, Anytown, USA",
            company = "Example Corp"
        )
    )
}
