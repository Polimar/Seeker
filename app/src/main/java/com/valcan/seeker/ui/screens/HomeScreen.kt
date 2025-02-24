package com.valcan.seeker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.ColorFilter
import com.valcan.seeker.R

@Composable
fun HomeScreen(
    nomeUtente: String,
    numeroVestiti: Int,
    numeroScarpe: Int,
    sesso: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header kawaii con emoji
        Text(
            text = "\uD83D\uDC9F Ciao $nomeUtente \uD83D\uDC9F",
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(top = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Messaggio kawaii
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
            )
        ) {
            Text(
                text = "✨ Cosa ci mettiamo oggi? ✨",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Contatori circolari uno sopra l'altro
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Contatore Vestiti
            CircularCounter(
                count = numeroVestiti,
                iconResId = if (sesso == "M") R.drawable.mdress else R.drawable.fdress,
                color = MaterialTheme.colorScheme.secondary
            )
            
            // Contatore Scarpe
            CircularCounter(
                count = numeroScarpe,
                iconResId = if (sesso == "M") R.drawable.msneakers else R.drawable.fshoe,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun CircularCounter(
    count: Int,
    iconResId: Int,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(CircleShape)
            .background(color.copy(alpha = 0.1f))
            .border(4.dp, color.copy(alpha = 0.5f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 8.dp)
            )
            
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = color,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
} 