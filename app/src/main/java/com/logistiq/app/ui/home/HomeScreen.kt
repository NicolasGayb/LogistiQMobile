package com.logistiq.app.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.logistiq.app.ui.components.FeatureCard
import com.logistiq.app.ui.components.LogistiQButton
import com.logistiq.app.ui.components.LogistiQScaffold
import com.logistiq.app.ui.components.LogistiQTitle
import androidx.compose.foundation.layout.Box

@Composable
fun HomeScreen(
    onGoToLogin: () -> Unit
) {
    LogistiQScaffold {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 16.dp,
                        bottom = 96.dp
                    ),
                verticalArrangement = Arrangement.Top
            ) {

                LogistiQTitle(text = "LogistiQ")

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Gestão inteligente, segura e escalável.",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(32.dp))

                FeatureCard(
                    title = "Gestão inteligente",
                    description = "Centralize e acompanhe operações com eficiência."
                )

                FeatureCard(
                    title = "Segurança de ponta",
                    description = "Autenticação segura e comunicação criptografada."
                )

                FeatureCard(
                    title = "Arquitetura escalável",
                    description = "Pronto para crescer com sua operação."
                )
            }

            LogistiQButton(
                text = "Começar agora",
                onClick = onGoToLogin,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
                    .fillMaxWidth(0.9f)
            )
        }
    }
}
