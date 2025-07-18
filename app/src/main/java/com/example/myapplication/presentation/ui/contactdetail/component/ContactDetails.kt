package com.example.myapplication.presentation.ui.contactdetail.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.domain.entity.Contact
import com.example.myapplication.presentation.component.ContactAvatar

/**
 * Composable that displays the details of a contact
 *
 * @param contact The contact to display
 */
@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val clipboardManager = LocalClipboardManager.current

        ContactAvatar(name = contact.name)
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = contact.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            if (contact.phone.isNotEmpty())
                                clipboardManager.setText(AnnotatedString(contact.phone))
                        }
                    )
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Contact Information Card
        ContactInfoCard(contact = contact)
    }
}

@Preview(showBackground = true)
@Composable
fun ContactDetailsPreview() {
    ContactDetails(
        contact = Contact(
            id = "1",
            name = "John Doe",
            phone = "+1 123 456 7890",
            email = "john.doe@example.com",
            address = "123 Main St, Anytown, USA",
            company = "Example Corp",
        )
    )
}
