package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(authManager: AuthManager) {

    val context = LocalContext.current

    val profileViewModel = remember { ProfileViewModel() }
    var userData by remember { mutableStateOf(User()) }
    var userAge by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        authManager.getCurrentUser()?.uid?.let { userId ->
            profileViewModel.getUserData(userId, context) { user ->
                userData = user
                userAge = calculateAge(user.dateOfBirth)
            }
        }
    }

    
    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "Name: ${userData.firstName} ${userData.lastName}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Gender: ${userData.gender}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Height: ${userData.height.toString()}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Weight: ${userData.weight.toString()}")

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Initial BMI: ${userData.initialBMI.toString()}")
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Current BMI: ${userData.currentBMI.toString()}")
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "BMI Category: ${userData.bmiCategory.toString()}")
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Date of Birth: ${userData.dateOfBirth.toString()} (${userAge})")

        }
    }
}

fun calculateAge(dateOfBirth: String): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val birthDate = dateFormat.parse(dateOfBirth)
    val cal = Calendar.getInstance()
    val currentDate = cal.time
    val diff = currentDate.time - birthDate.time
    val age = diff / (1000L * 60 * 60 * 24 * 365)
    return age.toString()
}