package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.FirestoreManager
import ca.centennial.finalproyect.utils.ProfileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen( profileViewModel: ProfileViewModel, authManager: AuthManager) {

    val user = authManager.getCurrentUser()

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

    val context = LocalContext.current

    val profileViewModel = remember { ProfileViewModel() }
    var userData by remember { mutableStateOf(User()) }

    LaunchedEffect(key1 = true) {
        authManager.getCurrentUser()?.uid?.let { userId ->
            profileViewModel.getUserData(userId, context) { user ->
                userData = user
            }
        }
    }

    
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${userData.firstName} ${userData.lastName}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.dateOfBirth)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.gender)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.height.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.weight.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.initialBMI.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.currentBMI.toString())

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = userData.bmiCategory)

        }
    }
}
