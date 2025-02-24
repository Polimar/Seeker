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
                var showUserSelection by remember { mutableStateOf(false) }
                var utentiState by remember { mutableStateOf<List<Utente>>(emptyList()) }
                
                LaunchedEffect(Unit) {
                    withContext(Dispatchers.IO) {
                        val users = utenteDao.getAll()
                        isFirstAccess = users.isEmpty()
                        if (!isFirstAccess) {
                            showUserSelection = true
                            utentiState = users
                        }
                    }
                }

                when {
                    isFirstAccess -> {
                        OnboardingScreen { nome, dataNascita, sesso ->
                            val utente = Utente(
                                nome = nome,
                                dataNascita = dataNascita,
                                sesso = sesso
                            )
                            coroutineScope.launch(Dispatchers.IO) {
                                val id = utenteDao.insert(utente)
                                currentUser = utenteDao.getById(id)
                                isFirstAccess = false
                            }
                        }
                    }
                    showUserSelection -> {
                        UserSelectionScreen(
                            utenti = utentiState,
                            onUserSelected = { utente ->
                                currentUser = utente
                                showUserSelection = false
                            }
                        )
                    }
                    else -> {
                        // Resto dell'app...
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