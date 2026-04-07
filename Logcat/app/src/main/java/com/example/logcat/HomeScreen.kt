package com.example.logcat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.logcat.ui.theme.LogcatTheme
import com.example.logcat.ui.theme.debugButtonColors
import com.example.logcat.ui.theme.errorButtonColors
import com.example.logcat.ui.theme.infoButtonColors
import com.example.logcat.ui.theme.warningButtonColors
object AppColors {
    val Black       = Color(0xFF0A0A0A)
    val DarkSurface = Color(0xFF141414)
    val CardBg      = Color(0xFF1C1C1C)
    val Red         = Color(0xFFBD0010)
    val RedDark     = Color(0xFF7A000A)
    val RedDeep     = Color(0xFF3B0000)
    val White       = Color(0xFFFFFFFF)
    val WhiteDim    = Color(0xFFCCCCCC)
    val Divider     = Color(0xFF2A2A2A)

    val RedGradient = Brush.verticalGradient(
        listOf(Red, RedDark, RedDeep)
    )
    val BlackGradient = Brush.verticalGradient(
        listOf(DarkSurface, Black)
    )
    val ButtonGradient = Brush.horizontalGradient(
        listOf(Red, RedDark)
    )
}

@Composable
fun HomeScreen(
    user: String = "Usuário",
    onLogout: () -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var showLog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = AppColors.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showLog = true },
                containerColor = AppColors.Red,
                contentColor = AppColors.White,
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Text("🪲", fontSize = 22.sp)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppColors.Black)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Logo + título
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(AppColors.CardBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.seon),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(70.dp)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "ATIVIDADE DE PAM 2",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = AppColors.White,
                        letterSpacing = 2.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Olá, $user",
                        fontSize = 13.sp,
                        color = AppColors.Red,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.sp
                    )
                }

                // Divisor vermelho
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .height(2.dp)
                        .background(
                            Brush.horizontalGradient(
                                listOf(Color.Transparent, AppColors.Red, Color.Transparent)
                            )
                        )
                )

                // Campo de nome
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "ALUNO",
                        fontSize = 11.sp,
                        color = AppColors.Red,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        placeholder = { Text("Digite seu nome...", color = AppColors.WhiteDim.copy(alpha = 0.4f)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = AppColors.Red,
                            unfocusedBorderColor = AppColors.Divider,
                            focusedTextColor = AppColors.White,
                            unfocusedTextColor = AppColors.White,
                            cursorColor = AppColors.Red,
                            focusedContainerColor = AppColors.CardBg,
                            unfocusedContainerColor = AppColors.CardBg
                        )
                    )
                }

                // Botões de nota
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "LANÇAR NOTA",
                        fontSize = 11.sp,
                        color = AppColors.Red,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GradeButton(
                            text = "I",
                            label = "Insuficiente",
                            buttonColors = errorButtonColors(),
                            modifier = Modifier.weight(1f)
                        ) { AppLogger.e(TAG, "App: $nome Nota I") }

                        GradeButton(
                            text = "R",
                            label = "Regular",
                            buttonColors = warningButtonColors(),
                            modifier = Modifier.weight(1f)
                        ) { AppLogger.w(TAG, "App: $nome Nota R") }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        GradeButton(
                            text = "B",
                            label = "Bom",
                            buttonColors = debugButtonColors(),
                            modifier = Modifier.weight(1f)
                        ) { AppLogger.d(TAG, "App: $nome Nota B") }

                        GradeButton(
                            text = "MB",
                            label = "Muito Bom",
                            buttonColors = infoButtonColors(),
                            modifier = Modifier.weight(1f)
                        ) { AppLogger.i(TAG, "App: $nome Nota MB") }
                    }
                }
            }
        }

        if (showLog) {
            LogPanel(onDismiss = { showLog = false })
        }
    }
}

@Composable
fun GradeButton(
    text: String,
    label: String,
    buttonColors: ButtonColors,
    modifier: Modifier = Modifier,
    block: () -> Unit
) {
    ElevatedButton(
        onClick = block,
        shape = RoundedCornerShape(12.dp),
        colors = buttonColors,
        elevation = ButtonDefaults.elevatedButtonElevation(6.dp),
        modifier = modifier.height(56.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = label, fontSize = 9.sp, fontWeight = FontWeight.Normal)
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    modifier: Modifier = Modifier,
    block: () -> Unit
) {
    ElevatedButton(
        onClick = block,
        shape = RoundedCornerShape(5.dp),
        colors = buttonColors,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "ATIVIDADE DE $name",
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
        color = AppColors.White
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF0A0A0A)
@Composable
fun HomeScreenPreview() {
    LogcatTheme {
        HomeScreen(user = "João", onLogout = {})
    }
}