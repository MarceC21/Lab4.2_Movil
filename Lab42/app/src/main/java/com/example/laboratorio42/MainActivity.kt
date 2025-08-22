package com.example.laboratorio42

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.laboratorio42.ui.theme.Laboratorio42Theme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio42Theme {
                // Estado de la lista de recetas
                val recetas = remember { mutableStateListOf<Receta>() }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        // Espacio para agregar las recetas
                        //Con función lambda para agregar una nueva receta a la lista
                        AgregarReceta(onAgregar = { nuevaReceta ->
                            recetas.add(nuevaReceta)
                        })

                        // Lista de recetas agregadas
                        ListaRecetas(recetas)
                    }
                }
            }

        }
    }
}

// Para mostrar la lista
@Composable
fun ListaRecetas(recetas: MutableList<Receta>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(recetas) { receta ->
            Tarjeta(
                receta = receta,
                onEliminar = { recetas.remove(receta)} //Aqui se pone la función para eliminar
            )
        }
    }
}

// Para ingresar datos
@Composable
//Riene como parametro una funcion lambda
fun AgregarReceta(onAgregar: (Receta) -> Unit, modifier: Modifier = Modifier) {
    //Estados del nombre y de la url
    var nombre by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        //Para agregar el nombre de la receta
        Text("Ingrese el nombre de la receta")
        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        //Para agregar la url de la imagen
        Text("Ingrese la URL de la imagen")
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        //Boton que al apachar va a agregar la receta a la lista
        Button(onClick = {
            //Valida que si se ingrese correctamente todo
            if (nombre.isNotEmpty() && url.isNotEmpty()) {
                onAgregar(Receta(nombre, url))
                nombre = ""
                url = ""
            }
        }) {
            Text("Agregar")
        }
    }
}

// Tarjeta que muestra una receta
@Composable
fun Tarjeta(receta: Receta, onEliminar: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = receta.nombre
            )

            Spacer(modifier = Modifier.height(8.dp))

            //Se debe usar AyncImage
            AsyncImage(
                model = receta.url,
                contentDescription = "Imagen de ${receta.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

           //Boton para eliminar el elemento de la lista
            Button(onClick = onEliminar) {
                Text("Eliminar")
            }

        }
    }
}

