package com.cobu.neutronai.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ChatScreen() {
    var messageText by remember { mutableStateOf(TextFieldValue("")) }
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Chat History (messages)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            messages.forEach { (text, isUser) ->
                ChatBubble(message = text, isUser = isUser)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Text Input Field
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp),
                maxLines = 1,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = {
                if (messageText.text.isNotBlank()) {
                    messages.add(messageText.text to true)  // Add user message
                    messages.add("Reply to: ${messageText.text}" to false)  // Add assistant message
                    messageText = TextFieldValue("")  // Clear input
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: String, isUser: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(if (isUser) Alignment.End else Alignment.Start)
    ) {
        Text(
            text = message,
            color = if (isUser) Color.White else Color.Black,
            modifier = Modifier
                .background(
                    color = if (isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        )
    }
}
