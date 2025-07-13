package com.example.myapplication.presentation.ui.editcontact.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.ui.editcontact.EditContactState

/**
 * Content for the edit contact screen
 *
 * @param state The state of the edit contact screen
 * @param onUpdateContact Callback when the contact is updated with all fields
 * @param onBackClick Callback when the back button is clicked
 */
@Composable
fun EditContactContent(
    state: EditContactState,
    onUpdateContact: (name: String, phone: String, email: String, company: String) -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (state) {
            is EditContactState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    strokeWidth = 4.dp
                )
            }

            is EditContactState.Editing -> {
                val contact = state.contact
                var name by remember(contact.id) { mutableStateOf(contact.name) }
                var phone by remember(contact.id) { mutableStateOf(contact.phone) }
                var email by remember(contact.id) { mutableStateOf(contact.email ?: "") }
                var company by remember(contact.id) { mutableStateOf(contact.company ?: "") }

                EditContactForm(
                    name = name,
                    phone = phone,
                    email = email,
                    company = company,
                    onNameChange = { name = it },
                    onPhoneChange = { phone = it },
                    onEmailChange = { email = it },
                    onCompanyChange = { company = it },
                    onUpdateClick = { onUpdateContact(name, phone, email, company) }
                )
            }

            is EditContactState.Error -> {
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

            is EditContactState.NotFound -> {
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onBackClick) {
                        Text("Go Back")
                    }
                }
            }

            is EditContactState.Success -> {
                // This state is handled by the parent component
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditContactContentPreview() {
    EditContactContent(
        state = EditContactState.Editing(
            contact = Contact(
                id = "1",
                name = "John Doe",
                phone = "+1 123 456 7890",
                email = "john.doe@example.com",
                address = "123 Main St, Anytown, USA",
                company = "Example Corp",

            )
        ),
        onUpdateContact = { _, _, _, _ -> },
        onBackClick = {}
    )
}
