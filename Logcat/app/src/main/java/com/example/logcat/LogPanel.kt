package com.example.logcat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log

object AppLogger {
    val logs = mutableStateListOf<LogEntry>()

    fun e(tag: String, msg: String) { logs.add(LogEntry("E", msg)); Log.e(tag, msg) }
    fun w(tag: String, msg: String) { logs.add(LogEntry("W", msg)); Log.w(tag, msg) }
    fun d(tag: String, msg: String) { logs.add(LogEntry("D", msg)); Log.d(tag, msg) }
    fun i(tag: String, msg: String) { logs.add(LogEntry("I", msg)); Log.i(tag, msg) }
}

data class LogEntry(val level: String, val message: String)

fun logColor(level: String) = when (level) {
    "E" -> Color(0xFFFF5252)
    "W" -> Color(0xFFFFD740)
    "D" -> Color(0xFF40C4FF)
    "I" -> Color(0xFF69F0AE)
    else -> Color.White
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogPanel(onDismiss: () -> Unit) {
    val listState = rememberLazyListState()
    val logs = AppLogger.logs

    LaunchedEffect(logs.size) {
        if (logs.isNotEmpty()) listState.animateScrollToItem(logs.size - 1)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1E1E1E),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(horizontal = 12.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("🪲 Debug Logcat", color = Color.White, fontSize = 16.sp)
                Row {
                    IconButton(onClick = { AppLogger.logs.clear() }) {
                        Icon(Icons.Default.Delete, contentDescription = "Limpar", tint = Color.Gray)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar", tint = Color.Gray)
                    }
                }
            }

            HorizontalDivider(color = Color.DarkGray)
            Spacer(modifier = Modifier.height(4.dp))

            if (logs.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum log ainda...", color = Color.Gray, fontSize = 13.sp)
                }
            } else {
                LazyColumn(state = listState) {
                    items(logs) { entry ->
                        Text(
                            text = "[${entry.level}] ${entry.message}",
                            color = logColor(entry.level),
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}