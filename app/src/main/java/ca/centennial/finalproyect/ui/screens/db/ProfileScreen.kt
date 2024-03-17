package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.FirestoreManager

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(firestore: FirestoreManager, authManager: AuthManager) {

    val authUserUID = authManager.getCurrentUser()?.uid




    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var datOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf(0.0) }
    var weight by remember { mutableStateOf(0.0) }
    var initialBMI by remember { mutableStateOf(0.0) }
    var currentBMI by remember { mutableStateOf(0.0) }
    var bmiCategory by remember { mutableStateOf(0.0) }



    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = )
        }
    }
}

@Composable
fun GetUser(user: User, firestore: FirestoreManager) {
    var
}