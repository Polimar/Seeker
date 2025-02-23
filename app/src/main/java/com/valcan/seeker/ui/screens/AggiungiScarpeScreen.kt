package com.valcan.seeker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.valcan.seeker.data.model.Scarpa
import com.valcan.seeker.utils.DateUtils

@Composable
fun AggiungiScarpeScreen(
    armadioId: Long,
    utenteId: Long,
    onSalva: (Scarpa) -> Unit,
    onBack: () -> Unit
) {
    var descrizione by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var colore by remember { mutableStateOf("") }
    var posizione by remember { mutableStateOf("") }
    var hasError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Torna indietro"
                )
            }
            Text(
                text = "Aggiungi Scarpe",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(48.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = descrizione,
            onValueChange = { 
                descrizione = it
                hasError = false 
            },
            label = { Text("Descrizione") },
            isError = hasError && descrizione.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        if (hasError && descrizione.isBlank()) {
            Text(
                text = "La descrizione è obbligatoria",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipo,
            onValueChange = { 
                tipo = it
                hasError = false 
            },
            label = { Text("Tipo") },
            isError = hasError && tipo.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        if (hasError && tipo.isBlank()) {
            Text(
                text = "Il tipo è obbligatorio",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = colore,
            onValueChange = { 
                colore = it
                hasError = false 
            },
            label = { Text("Colore") },
            isError = hasError && colore.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        if (hasError && colore.isBlank()) {
            Text(
                text = "Il colore è obbligatorio",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = posizione,
            onValueChange = { 
                posizione = it
                hasError = false 
            },
            label = { Text("Posizione nell'armadio") },
            isError = hasError && posizione.isBlank(),
            modifier = Modifier.fillMaxWidth()
        )
        if (hasError && posizione.isBlank()) {
            Text(
                text = "La posizione è obbligatoria",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (descrizione.isBlank() || tipo.isBlank() || colore.isBlank() || posizione.isBlank()) {
                    hasError = true
                } else {
                    onSalva(
                        Scarpa(
                            descrizione = descrizione,
                            tipo = tipo,
                            colore = colore,
                            armadioId = armadioId,
                            posizione = posizione,
                            utenteId = utenteId,
                            dataInserimento = DateUtils.getCurrentDateTime()
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva")
        }
    }
} 