package com.valcan.seeker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valcan.seeker.data.model.Utente
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable

@Composable
fun GestioneUtentiScreen(
    utenti: List<Utente>,
    currentUser: Utente?,
    onAddUser: () -> Unit,
    onDeleteUser: (Utente) -> Unit,
    onSelectUser: (Utente) -> Unit,
    onBack: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf<Utente?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Torna indietro"
                )
            }
            Text(
                text = "Gestione Utenti",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        if (utenti.isEmpty()) {
            Text(
                text = "Nessun utente presente",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(utenti) { utente ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectUser(utente) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (currentUser?.id == utente.id) 
                                MaterialTheme.colorScheme.primaryContainer 
                            else 
                                MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = utente.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Data di nascita: ${utente.dataNascita}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = if (utente.sesso == "M") "Maschio" else "Femmina",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                if (currentUser?.id == utente.id) {
                                    Text(
                                        text = "Utente selezionato",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                            IconButton(onClick = { showDeleteDialog = utente }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Elimina utente"
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onAddUser,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Aggiungi Nuovo Utente")
        }
    }

    // Dialog di conferma eliminazione
    showDeleteDialog?.let { utente ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Conferma eliminazione") },
            text = { Text("Sei sicuro di voler eliminare l'utente ${utente.nome}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteUser(utente)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Conferma")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Annulla")
                }
            }
        )
    }
} 