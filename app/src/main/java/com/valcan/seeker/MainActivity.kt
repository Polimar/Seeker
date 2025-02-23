package com.valcan.seeker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.valcan.seeker.ui.theme.SeekerTheme
import com.valcan.seeker.data.db.DatabaseHelper
import com.valcan.seeker.data.dao.ArmadioDao
import com.valcan.seeker.data.dao.UtenteDao
import com.valcan.seeker.data.dao.VestitoDao
import com.valcan.seeker.data.dao.ScarpaDao
import com.valcan.seeker.data.model.Armadio
import com.valcan.seeker.data.model.Utente
import com.valcan.seeker.data.model.Vestito
import com.valcan.seeker.ui.navigation.*
import com.valcan.seeker.ui.screens.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import androidx.compose.runtime.rememberCoroutineScope

class MainActivity : ComponentActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var armadioDao: ArmadioDao
    private lateinit var utenteDao: UtenteDao
    private lateinit var vestitoDao: VestitoDao
    private lateinit var scarpaDao: ScarpaDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Inizializzazione del database e dei DAO
        dbHelper = DatabaseHelper(this)
        val db = dbHelper.writableDatabase
        
        armadioDao = ArmadioDao(db)
        utenteDao = UtenteDao(db)
        vestitoDao = VestitoDao(db)
        scarpaDao = ScarpaDao(db)

        setContent {
            SeekerTheme {
                var currentRoute by remember { mutableStateOf("Home") }
                var currentUser by remember { mutableStateOf<Utente?>(null) }
                var isFirstAccess by remember { mutableStateOf(true) }
                var showGestioneUtenti by remember { mutableStateOf(false) }
                var showAddUser by remember { mutableStateOf(false) }
                var armadiState by remember { mutableStateOf<List<Armadio>>(emptyList()) }
                var utentiState by remember { mutableStateOf<List<Utente>>(emptyList()) }
                var selectedArmadioId by remember { mutableStateOf<Long?>(null) }

                val coroutineScope = rememberCoroutineScope()

                // Funzione per aggiornare gli armadi
                suspend fun updateArmadi() {
                    withContext(Dispatchers.IO) {
                        currentUser?.let { user ->
                            armadiState = armadioDao.getArmadiWithVestitiByUtenteId(user.id)
                        }
                    }
                }

                // Funzione per aggiornare gli utenti
                suspend fun updateUtenti() {
                    withContext(Dispatchers.IO) {
                        utentiState = utenteDao.getAll()
                    }
                }

                // Effetto per caricare i dati iniziali
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        val users = utenteDao.getAll()
                        isFirstAccess = users.isEmpty()
                        if (!isFirstAccess) {
                            currentUser = users.firstOrNull()
                            updateArmadi()
                        }
                    }
                }

                // Effetto per aggiornare gli armadi quando cambia l'utente
                LaunchedEffect(currentUser) {
                    updateArmadi()
                }

                when {
                    isFirstAccess -> {
                        OnboardingScreen { nome, dataNascita ->
                            val utente = Utente(
                                nome = nome,
                                dataNascita = dataNascita,
                                foto = null
                            )
                            val id = utenteDao.insert(utente)
                            currentUser = utenteDao.getById(id)
                            isFirstAccess = false
                        }
                    }
                    showAddUser -> {
                        OnboardingScreen { nome, dataNascita ->
                            val utente = Utente(
                                nome = nome,
                                dataNascita = dataNascita,
                                foto = null
                            )
                            utenteDao.insert(utente)
                            showAddUser = false
                            showGestioneUtenti = true  // Torna alla schermata di gestione utenti
                        }
                    }
                    showGestioneUtenti -> {
                        LaunchedEffect(Unit) {
                            updateUtenti()
                        }
                        
                        GestioneUtentiScreen(
                            utenti = utentiState,
                            currentUser = currentUser,
                            onAddUser = { showAddUser = true },
                            onDeleteUser = { utente ->
                                coroutineScope.launch(Dispatchers.IO) {
                                    utenteDao.delete(utente)
                                    if (currentUser?.id == utente.id) {
                                        currentUser = utenteDao.getAll().firstOrNull()
                                    }
                                    updateUtenti()
                                }
                            },
                            onSelectUser = { utente ->
                                currentUser = utente
                            },
                            onBack = { showGestioneUtenti = false }
                        )
                    }
                    else -> {
                        Scaffold(
                            bottomBar = {
                                BottomNavigation(
                                    currentRoute = currentRoute,
                                    onNavigate = { item ->
                                        currentRoute = item::class.simpleName ?: "Home"
                                    }
                                )
                            }
                        ) { paddingValues ->
                            Box(modifier = Modifier.padding(paddingValues)) {
                                when (currentRoute) {
                                    "Home" -> HomeScreen(
                                        nomeUtente = currentUser?.nome ?: "",
                                        numeroVestiti = currentUser?.let { 
                                            vestitoDao.getByUtenteId(it.id).size 
                                        } ?: 0,
                                        numeroScarpe = currentUser?.let { 
                                            scarpaDao.getByUtenteId(it.id).size 
                                        } ?: 0
                                    )
                                    "Vestiti" -> VestitiScreen(
                                        vestiti = currentUser?.let { 
                                            vestitoDao.getByUtenteId(it.id) 
                                        } ?: emptyList(),
                                        currentUser = currentUser?.nome ?: "",
                                        onAddVestito = { /* TODO: Implementare l'aggiunta di un vestito */ }
                                    )
                                    "Armadio" -> ArmadioScreen(
                                        armadi = armadiState,
                                        currentUser = currentUser?.nome ?: "",
                                        onAddArmadio = { armadio ->
                                            coroutineScope.launch(Dispatchers.IO) {
                                                armadioDao.insert(armadio)
                                                updateArmadi()
                                            }
                                        },
                                        onUpdateArmadio = { armadio ->
                                            coroutineScope.launch(Dispatchers.IO) {
                                                armadioDao.update(armadio)
                                                updateArmadi()
                                            }
                                        },
                                        onDeleteArmadio = { armadio ->
                                            coroutineScope.launch(Dispatchers.IO) {
                                                armadioDao.delete(armadio)
                                                updateArmadi()
                                            }
                                        },
                                        onAddVestito = { armadioId ->
                                            // Naviga alla schermata di aggiunta vestiti
                                            currentRoute = "Vestiti"
                                            selectedArmadioId = armadioId
                                        },
                                        onAddScarpe = { armadioId ->
                                            // Naviga alla schermata di aggiunta scarpe
                                            currentRoute = "Scarpe"
                                            selectedArmadioId = armadioId
                                        },
                                        onArmadioClick = { /* Non più necessario */ }
                                    )
                                    "Cerca" -> CercaScreen()
                                    "Impostazioni" -> ImpostazioniScreen(
                                        onGestioneUtentiClick = { showGestioneUtenti = true }
                                    )
                                    else -> Text("Schermata non trovata")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper.close()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SeekerTheme {
        Greeting("Android")
    }
}