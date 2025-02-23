package com.valcan.seeker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import com.valcan.seeker.data.model.Armadio

@Composable
fun ArmadioScreen(
    armadi: List<Armadio>,
    currentUser: String,
    onAddArmadio: (Armadio) -> Unit,
    onUpdateArmadio: (Armadio) -> Unit,
    onDeleteArmadio: (Armadio) -> Unit,
    onArmadioClick: (Armadio) -> Unit,
    onAddVestito: (Long) -> Unit,
    onAddScarpe: (Long) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var armadioToEdit by remember { mutableStateOf<Armadio?>(null) }
    var showDeleteDialog by remember { mutableStateOf<Armadio?>(null) }
    var showSelectionDialog by remember { mutableStateOf<Armadio?>(null) }

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
            Text(
                text = "I miei Armadi",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aggiungi armadio",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (armadi.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nessun armadio presente",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(armadi) { armadio ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showSelectionDialog = armadio },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = armadio.nome,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Row {
                                    IconButton(onClick = { armadioToEdit = armadio }) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Modifica armadio"
                                        )
                                    }
                                    IconButton(onClick = { showDeleteDialog = armadio }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Elimina armadio"
                                        )
                                    }
                                }
                            }
                            Text(
                                text = "Posizione: ${armadio.posizione}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Vestiti: ${armadio.numeroVestiti}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = "Scarpe: ${armadio.numeroScarpe}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialog per aggiungere/modificare armadio
    if (showAddDialog || armadioToEdit != null) {
        AggiungiModificaArmadioDialog(
            armadio = armadioToEdit,
            onDismiss = {
                showAddDialog = false
                armadioToEdit = null
            },
            onConfirm = { nome, posizione ->
                if (armadioToEdit != null) {
                    onUpdateArmadio(armadioToEdit!!.copy(
                        nome = nome,
                        posizione = posizione
                    ))
                } else {
                    onAddArmadio(Armadio(
                        nome = nome,
                        posizione = posizione
                    ))
                }
                showAddDialog = false
                armadioToEdit = null
            }
        )
    }

    // Dialog di conferma eliminazione
    showDeleteDialog?.let { armadio ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Conferma eliminazione") },
            text = { Text("Sei sicuro di voler eliminare l'armadio ${armadio.nome}?") },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteArmadio(armadio)
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

    // Aggiungi il dialog di selezione
    showSelectionDialog?.let { armadio ->
        AlertDialog(
            onDismissRequest = { showSelectionDialog = null },
            title = { Text("Cosa vuoi aggiungere?") },
            text = { Text("Seleziona il tipo di elemento da aggiungere all'armadio ${armadio.nome}") },
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            onAddVestito(armadio.id)
                            showSelectionDialog = null
                        }
                    ) {
                        Text("Vestito")
                    }
                    Button(
                        onClick = {
                            onAddScarpe(armadio.id)
                            showSelectionDialog = null
                        }
                    ) {
                        Text("Scarpe")
                    }
                }
            },
            dismissButton = {
                TextButton(onClick = { showSelectionDialog = null }) {
                    Text("Annulla")
                }
            }
        )
    }
}

@Composable
private fun AggiungiModificaArmadioDialog(
    armadio: Armadio?,
    onDismiss: () -> Unit,
    onConfirm: (nome: String, posizione: String) -> Unit
) {
    var nome by remember { mutableStateOf(armadio?.nome ?: "") }
    var posizione by remember { mutableStateOf(armadio?.posizione ?: "") }
    var hasError by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { 
            Text(if (armadio == null) "Aggiungi Armadio" else "Modifica Armadio") 
        },
        text = {
            Column {
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
                if (hasError && nome.isBlank()) {
                    Text(
                        text = "Il nome è obbligatorio",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = posizione,
                    onValueChange = { 
                        posizione = it
                        hasError = false
                    },
                    label = { Text("Posizione") },
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (nome.isBlank() || posizione.isBlank()) {
                        hasError = true
                    } else {
                        onConfirm(nome, posizione)
                    }
                }
            ) {
                Text(if (armadio == null) "Aggiungi" else "Modifica")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
} 