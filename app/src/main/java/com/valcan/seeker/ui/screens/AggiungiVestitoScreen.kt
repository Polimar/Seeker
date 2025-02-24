package com.valcan.seeker.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import com.valcan.seeker.R
import com.valcan.seeker.data.model.Vestito
import com.valcan.seeker.data.model.Armadio
import com.valcan.seeker.utils.DateUtils
import com.valcan.seeker.utils.ImageUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AggiungiVestitoScreen(
    armadioId: Long,
    utenteId: Long,
    armadi: List<Armadio>,
    onSalva: (Vestito) -> Unit,
    onBack: () -> Unit
) {
    var descrizione by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var colore by remember { mutableStateOf("") }
    var posizione by remember { mutableStateOf("") }
    var selectedArmadioId by remember { mutableStateOf(armadioId) }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var showArmadioMenu by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val photoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // La foto è stata salvata nell'Uri temporaneo
            // Verrà processata quando l'utente salva il vestito
        }
    }

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
                text = "Aggiungi Vestito",
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
            },
            label = { Text("Descrizione") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tipo,
            onValueChange = { 
                tipo = it
            },
            label = { Text("Tipo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = colore,
            onValueChange = { 
                colore = it
            },
            label = { Text("Colore") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = posizione,
            onValueChange = { posizione = it },
            label = { Text("Posizione") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = showArmadioMenu,
            onExpandedChange = { showArmadioMenu = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = armadi.find { it.id == selectedArmadioId }?.nome ?: "",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) },
                label = { Text("Armadio") },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = showArmadioMenu,
                onDismissRequest = { showArmadioMenu = false }
            ) {
                armadi.forEach { armadio ->
                    DropdownMenuItem(
                        text = { Text(armadio.nome) },
                        onClick = {
                            selectedArmadioId = armadio.id
                            showArmadioMenu = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        IconButton(
            onClick = {
                val uri = ImageUtils.createTempImageUri(context)
                fotoUri = uri
                photoLauncher.launch(uri)
            },
            modifier = Modifier.size(48.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.addphoto),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val fotoPath = fotoUri?.let { uri ->
                    ImageUtils.processAndSaveImage(context, uri)
                }
                
                onSalva(
                    Vestito(
                        descrizione = descrizione,
                        tipo = tipo,
                        colore = colore,
                        armadioId = selectedArmadioId,
                        posizione = posizione,
                        utenteId = utenteId,
                        dataInserimento = DateUtils.getCurrentDate(),
                        foto = fotoPath
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salva")
        }
    }
} 