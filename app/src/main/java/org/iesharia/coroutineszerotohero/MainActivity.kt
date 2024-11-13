package org.iesharia.coroutineszerotohero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.iesharia.coroutineszerotohero.ui.theme.CoroutinesZeroToHeroTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoroutinesZeroToHeroTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun UserListScreen(modifier: Modifier = Modifier) {
    val apiService = remember { createApiService() }
    var users by remember { mutableStateOf<List<UserDataResponse>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                users = response.body() ?: emptyList()
            } else {
                errorMessage = "Error: ${response.message()}"
            }
        } catch (e: Exception) {
            errorMessage = "Exception: ${e.message}"
        }
    }

    if (errorMessage != null) {
        Text(text = errorMessage ?: "Error desconocido")
    } else {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(users) { user ->
                UserItem(user = user)
            }
        }
    }
}

@Composable
fun UserItem(user: UserDataResponse) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "ID: ${user.id}")
        Text(text = "Nombre: ${user.name}")
        Text(text = "Usuario: ${user.username}")
        Text(text = "Email: ${user.email}")
        Text(text = "Direccion: ${user.address.street}, ${user.address.suite}, ${user.address.city}, ${user.address.zipcode}")
        Text(text = "Localizacion: (${user.address.geo.lat}, ${user.address.geo.lng})")
        Text(text = "Telefono: ${user.phone}")
        Text(text = "Web: ${user.website}")
        Text(text = "Compañia: ${user.company.name}, ${user.company.catchPhrase}, ${user.company.bs}")
        Divider(modifier = Modifier.padding(vertical = 8.dp))
    }
}

fun createApiService(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}
