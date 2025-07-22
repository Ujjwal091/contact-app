package com.example.myapplication.presentation.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Extracts the first valid alphanumeric character from a string.
 * Skips over emojis and special characters.
 *
 * @param name The input string
 * @return The first valid character in uppercase, or "?" if no valid character is found
 */
fun getFirstValidCharacter(name: String): String {
    val trimmedName = name.trim()
    if (trimmedName.isEmpty()) {
        return "?"
    }

    // Find the first letter or digit in the name
    for (char in trimmedName) {
        if (char.isLetterOrDigit()) {
            return char.toString().uppercase()
        }
    }

    // If no valid character is found, return "?"
    return "?"
}

/**
 * Avatar for a contact
 *
 * @param name The name of the contact
 */
@Composable
fun ContactAvatar(name: String) {
    Surface(
        modifier = Modifier.size(120.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primaryContainer,
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = getFirstValidCharacter(name),
                fontSize = 48.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactAvatarPreview() {
    MaterialTheme {
        ContactAvatar(name = "John Doe")
    }
}