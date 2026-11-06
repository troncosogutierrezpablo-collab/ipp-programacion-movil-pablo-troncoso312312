package com.example.ipp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ipp.ui.theme.IPPTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IPPTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaRecordatorios(
                        recordatorios = recordatoriosSimulados,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// -1 modelo de datos

sealed class Prioridad {
    object Alta : Prioridad()
    object Media : Prioridad()
}

sealed class Estado {
    object Pendiente : Estado()
    object Completado : Estado()
}

data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String? = null,
    val categoria: String,
    val prioridad: Prioridad,
    val estado: Estado
)

// -2. DATOS SIMULADOS (Lists, Sets, Maps)

val categoriasUnicas: Set<String> = setOf("Trabajo", "Personal", "Salud", "Trabajo")

val coloresCategoria: Map<String, Long> = mapOf(
    "Trabajo" to 0xFF2196F3,
    "Personal" to 0xFF4CAF50,
    "Salud" to 0xFFF44336
)

val recordatoriosSimulados: List<Recordatorio> = listOf(
    Recordatorio(1, "Enviar reporte", "Reporte mensual de ventas", "Trabajo", Prioridad.Alta, Estado.Pendiente),
    Recordatorio(2, "Comprar víveres", null, "Personal", Prioridad.Media, Estado.Pendiente),
    Recordatorio(3, "Tomar medicación", "Pastilla de la presión", "Salud", Prioridad.Alta, Estado.Completado)
)

// 3- interfaz grafica

@Composable
fun PantallaRecordatorios(recordatorios: List<Recordatorio>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(recordatorios) { recordatorio ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = recordatorio.titulo, style = MaterialTheme.typography.titleLarge)
                    Text(text = "Categoría: ${recordatorio.categoria}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Prioridad: ${recordatorio.prioridad.javaClass.simpleName}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Estado: ${recordatorio.estado.javaClass.simpleName}", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}