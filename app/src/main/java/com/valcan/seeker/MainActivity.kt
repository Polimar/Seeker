package com.valcan.seeker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
                var isFirstAccess by remember { mutableStateOf(utenteDao.getAll().isEmpty()) }

                if (isFirstAccess) {
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
                } else {
                    if (currentUser == null) {
                        // Carica il primo utente (per ora gestiamo un solo utente)
                        currentUser = utenteDao.getAll().firstOrNull()
                    }

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
                            "Vestiti" -> VestitiScreen()
                            "Armadio" -> ArmadioScreen()
                            "Cerca" -> CercaScreen()
                            "Impostazioni" -> ImpostazioniScreen()
                            else -> Text("Schermata non trovata")
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