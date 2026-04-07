package com.example.logcat

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(onLogin: (String) -> Unit, onRegisterClick: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper(context) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Black),
        contentAlignment = Alignment.Center
    ) {
        // Brilho vermelho sutil no topo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(AppColors.RedDeep.copy(alpha = 0.6f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.CardBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seon),
                    contentDescription = "Logo",
                    modifier = Modifier.size(72.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "BEM-VINDO",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.White,
                letterSpacing = 4.sp
            )
            Text(
                text = "Faça login para continuar",
                fontSize = 13.sp,
                color = AppColors.WhiteDim.copy(alpha = 0.5f),
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(8.dp))

            // Linha decorativa
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(listOf(AppColors.Red, AppColors.RedDark)),
                        RoundedCornerShape(2.dp)
                    )
            )

            Spacer(Modifier.height(32.dp))

            // Card do formulário
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.CardBg)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    // Campo usuário
                    FieldLabel("USUÁRIO")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = user,
                        onValueChange = { user = it },
                        placeholder = { Text("seu usuário", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = {
                            Icon(Icons.Filled.Person, contentDescription = null, tint = AppColors.Red)
                        },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Campo senha
                    FieldLabel("SENHA")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("••••••••", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, contentDescription = null, tint = AppColors.Red)
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(24.dp))

                    // Botão entrar com gradiente
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(AppColors.ButtonGradient)
                    ) {
                        Button(
                            onClick = {
                                if (dbHelper.authenticate(user, password)) {
                                    onLogin(user)
                                } else {
                                    Toast.makeText(context, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                "ENTRAR",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 3.sp,
                                color = AppColors.White
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            TextButton(onClick = onRegisterClick) {
                Text(
                    "Não tem conta?  ",
                    color = AppColors.WhiteDim.copy(alpha = 0.4f),
                    fontSize = 13.sp
                )
                Text(
                    "Cadastre-se",
                    color = AppColors.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(onClick = { /* esqueceu senha */ }) {
                Text(
                    "Esqueceu a senha?",
                    color = AppColors.WhiteDim.copy(alpha = 0.3f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 10.sp,
        color = AppColors.Red,
        fontWeight = FontWeight.Bold,
        letterSpacing = 2.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun darkFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = AppColors.Red,
    unfocusedBorderColor = AppColors.Divider,
    focusedTextColor = AppColors.White,
    unfocusedTextColor = AppColors.White,
    cursorColor = AppColors.Red,
    focusedContainerColor = AppColors.DarkSurface,
    unfocusedContainerColor = AppColors.DarkSurface,
    focusedLabelColor = AppColors.Red,
    unfocusedLabelColor = AppColors.WhiteDim.copy(alpha = 0.5f)
)