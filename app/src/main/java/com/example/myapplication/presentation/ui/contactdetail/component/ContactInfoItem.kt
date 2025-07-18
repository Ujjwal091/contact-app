package com.example.myapplication.presentation.ui.contactdetail.component

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri

/**
 * Item displaying a piece of contact information
 *
 * @param icon The icon to display
 * @param label The label for the information
 * @param value The value of the information
 * @param isPhone Whether this item represents a phone number
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactInfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    isPhone: Boolean = false
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(
                if (isPhone) {
                    Modifier.combinedClickable(
                        onClick = {
                            // Open dialer with phone number
                            val intent = Intent(Intent.ACTION_DIAL).apply {
                                data = "tel:$value".toUri()
                            }
                            context.startActivity(intent)
                        },
                        onLongClick = {
                            // Copy phone number to clipboard
                            clipboardManager.setText(AnnotatedString(value))
                        }
                    )
                } else {
                    Modifier
                }
            )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactInfoItemPreview() {
    MaterialTheme {
        ContactInfoItem(
            icon = Icons.Default.Phone,
            label = "Phone",
            value = "+1 123 456 7890"
        )
    }
}