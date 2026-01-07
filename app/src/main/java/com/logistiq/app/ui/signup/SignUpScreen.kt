package com.logistiq.app.ui.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun SignupScreen(
    onBackClick: () -> Unit,
    onSignupSuccess: () -> Unit,
    viewModel: SignupViewModel = hiltViewModel()
) {
    val signupState by viewModel.signupState.collectAsState()
    val context = LocalContext.current

    // Mostra toast de sucesso antes de navegar
    LaunchedEffect(signupState) {
        if (signupState is SignupState.Success) {
            Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            onSignupSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "LogistiQ",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Crie sua conta 🚀",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = viewModel.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text("Seu nome") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.email,
                    onValueChange = viewModel::onEmailChange,
                    label = { Text("Seu email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = viewModel::onPasswordChange,
                    label = { Text("Sua senha") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.companyName,
                    onValueChange = viewModel::onCompanyNameChange,
                    label = { Text("Razão Social") },
                    leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.companyCNPJ,
                    onValueChange = viewModel::onCompanyCNPJChange,
                    label = { Text("CNPJ") },
                    leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp)
                )

                Spacer(modifier = Modifier.height(28.dp))

                Button(
                    onClick = { viewModel.signup() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    enabled = signupState !is SignupState.Loading && viewModel.isFormValid
                ) {
                    Text(
                        text = if (signupState is SignupState.Loading) "Criando conta..." else "Cadastrar",
                        fontWeight = FontWeight.Bold
                    )
                }

                if (signupState is SignupState.Error) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = (signupState as SignupState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                TextButton(onClick = onBackClick) {
                    Text("Já tem conta? Faça login")
                }
            }
        }
    }
}
