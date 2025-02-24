package com.valcan.seeker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.valcan.seeker.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OnboardingScreen(
    onComplete: (nome: String, dataNascita: String, sesso: String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var dataNascita by remember { mutableStateOf("") }
    var selectedSesso by remember { mutableStateOf<String?>(null) }
    var hasError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Benvenuto su SeeKer",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nome,
            onValueChange = { 
                nome = it
                hasError = false
            },
            label = { Text("Nome") },
            isError = hasError && nome.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = dataNascita,
            onValueChange = { 
                dataNascita = it
                hasError = false
            },
            label = { Text("Data di nascita (dd/mm/yyyy)") },
            isError = hasError && dataNascita.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.usermale),
                contentDescription = "Utente maschio",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { selectedSesso = "M" }
                    .border(
                        width = 2.dp,
                        color = if (selectedSesso == "M") 
                            MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
            
            Image(
                painter = painterResource(id = R.drawable.userfemale),
                contentDescription = "Utente femmina",
                modifier = Modifier
                    .size(80.dp)
                    .clickable { selectedSesso = "F" }
                    .border(
                        width = 2.dp,
                        color = if (selectedSesso == "F") 
                            MaterialTheme.colorScheme.primary 
                        else MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    )
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (nome.isBlank() || dataNascita.isBlank() || selectedSesso == null) {
                    hasError = true
                } else {
                    onComplete(nome, dataNascita, selectedSesso!!)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continua")
        }
    }
}

private fun isValidDate(date: String): Boolean {
    return try {
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)
        true
    } catch (e: Exception) {
        false
    }
} 