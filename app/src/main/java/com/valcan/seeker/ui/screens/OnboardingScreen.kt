package com.valcan.seeker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OnboardingScreen(
    onComplete: (String, String) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var dataNascita by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Benvenuto in Seeker!",
            style = MaterialTheme.typography.headlineMedium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = dataNascita,
            onValueChange = { dataNascita = it },
            label = { Text("Data di nascita (DD/MM/YYYY)") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { onComplete(nome, dataNascita) },
            modifier = Modifier.fillMaxWidth(),
            enabled = nome.isNotBlank() && isValidDate(dataNascita)
        ) {
            Text("Inizia")
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