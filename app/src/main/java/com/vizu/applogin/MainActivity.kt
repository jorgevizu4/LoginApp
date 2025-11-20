package com.vizu.applogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vizu.applogin.ui.theme.AppLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLoginTheme {
                NavHostLogin()
            }
        }
    }
}

@Composable
fun NavHostLogin() {
    val navegar = rememberNavController()
    NavHostPrincipal(navegar)
}

@Composable
fun NavHostPrincipal(navegar: NavHostController) {
    NavHost(
        navController = navegar,
        startDestination = "login"
    ) {
        composable("login") { PantallaInicio(navegar) }
        composable("bienvenido") { PantallaDetalle(navegar) }
    }
}


@Composable
fun PantallaInicio(navegar: NavHostController) {
    var usuario by rememberSaveable() { mutableStateOf("") }
    var password by rememberSaveable() { mutableStateOf("") }
    var mensajeError by rememberSaveable() { mutableStateOf("") }
    var login by rememberSaveable() { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "AppLogin",
            modifier = Modifier.padding(8.dp)
        )
        OutlinedTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.padding(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            )
        )
        Button(
            onClick = {
                if (usuario.equals("admin") && password.equals("admin")) {
                    navegar.navigate("bienvenido")
                    login = true
                } else {
                    mensajeError = "Usuario o contraseña incorrectos"
                    login = false
                }
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Login")
        }
        if (!login) {
            Text(
                text = mensajeError,
                color = Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun PantallaDetalle(navegar: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido",
            modifier = Modifier.padding(8.dp),
            fontSize = 36.sp
        )
        Image(
            painter = painterResource(R.drawable.facebook),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Button(
            onClick = { navegar.popBackStack() }
        ) {
            Text("Volver a inicio")
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    AppLoginTheme {
        PantallaDetalle(rememberNavController())
    }
}

