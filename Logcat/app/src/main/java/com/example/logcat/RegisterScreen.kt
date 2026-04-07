package com.example.logcat

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegisterScreen(onRegisterComplete: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    val dbHelper = remember { DatabaseHelper.getInstance(context.applicationContext) }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Black)
    ) {
        // Brilho vermelho sutil no topo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(AppColors.RedDeep.copy(alpha = 0.5f), Color.Transparent)
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 28.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(AppColors.CardBg),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.seon),
                    contentDescription = "Logo",
                    modifier = Modifier.size(66.dp)
                )
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "CADASTRO",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AppColors.White,
                letterSpacing = 4.sp
            )
            Text(
                text = "Crie sua conta",
                fontSize = 13.sp,
                color = AppColors.WhiteDim.copy(alpha = 0.4f),
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(3.dp)
                    .background(
                        Brush.horizontalGradient(listOf(AppColors.Red, AppColors.RedDark)),
                        RoundedCornerShape(2.dp)
                    )
            )

            Spacer(Modifier.height(28.dp))

            // Card do formulário
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.CardBg)
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    FieldLabel("NOME DE USUÁRIO")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text("seu usuário", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Filled.Person, null, tint = AppColors.Red) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(14.dp))

                    FieldLabel("E-MAIL")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("email@exemplo.com", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Filled.Email, null, tint = AppColors.Red) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(14.dp))

                    FieldLabel("SENHA")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("••••••••", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Filled.Lock, null, tint = AppColors.Red) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(14.dp))

                    FieldLabel("CONFIRMAR SENHA")
                    Spacer(Modifier.height(6.dp))
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        placeholder = { Text("••••••••", color = AppColors.WhiteDim.copy(alpha = 0.3f)) },
                        leadingIcon = { Icon(Icons.Filled.Lock, null, tint = AppColors.Red) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = darkFieldColors()
                    )

                    Spacer(Modifier.height(24.dp))

                    // Botão cadastrar com gradiente
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(AppColors.ButtonGradient)
                    ) {
                        Button(
                            onClick = {
                                if (username.isBlank()) {
                                    Toast.makeText(context, "Informe o nome de usuário", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (password.isBlank()) {
                                    Toast.makeText(context, "Informe a senha", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                if (password != confirmPassword) {
                                    Toast.makeText(context, "As senhas não coincidem", Toast.LENGTH_SHORT).show()
                                    return@Button
                                }
                                val success = dbHelper.create(username.trim(), email.trim(), password.trim())
                                if (success) {
                                    Toast.makeText(context, "Cadastro realizado!", Toast.LENGTH_SHORT).show()
                                    onRegisterComplete()
                                }
                            },
                            modifier = Modifier.fillMaxSize(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                        ) {
                            Text(
                                "CADASTRAR",
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

            TextButton(onClick = onRegisterComplete) {
                Text(
                    "Já tem uma conta?  ",
                    color = AppColors.WhiteDim.copy(alpha = 0.4f),
                    fontSize = 13.sp
                )
                Text(
                    "Fazer login",
                    color = AppColors.Red,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
