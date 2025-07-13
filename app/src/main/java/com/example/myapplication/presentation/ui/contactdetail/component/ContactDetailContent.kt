package com.example.myapplication.presentation.ui.contactdetail.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.contactdetail.ContactDetailState

/**
 * Content for the contact detail screen
 *
 * @param state The state of the contact detail screen
 */
@Composable
fun ContactDetailContent(
    state: ContactDetailState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is ContactDetailState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 4.dp
                )
            }

            is ContactDetailState.Success -> {
                val contact = state.contact
                ContactDetails(contact = contact)
            }

            is ContactDetailState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Error",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.message,
                        fontSize = 16.sp
                    )
                }
            }

            is ContactDetailState.NotFound -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Contact Not Found",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDetailContentPreview() {
    MaterialTheme {
        ContactDetailContent(
            state = ContactDetailState.Success(
                contact = Contact(
                    id = "1",
                    name = "John Doe",
                    phone = "+1 123 456 7890",
                    email = "john.doe@example.com",
                    address = "123 Main St, Anytown, USA",
                    company = "Example Corp",
                )
            )
        )
    }
}
