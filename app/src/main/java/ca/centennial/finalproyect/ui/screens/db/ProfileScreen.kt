package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.ui.theme.PurpleGrey40
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.ProfileViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, authManager: AuthManager) {

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_profile),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User Profile",
                color = Color(0xFF1B5E20),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Basic Info Section (First Name, Last Name, Date of Birth, Gender)

            Text(
                text = "Basic Info Section",
                style = TextStyle(fontSize = 12.sp, color = PurpleGrey40)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Name: ${userData.firstName} ${userData.lastName}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Date of Birth: ${userData.dateOfBirth}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Gender: ${userData.gender}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Body Measurements",
                style = TextStyle(fontSize = 12.sp, color = PurpleGrey40)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Body Info Section (Height and Weight)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Height: ${userData.height}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Weight: ${userData.weight}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Initial BMI: ${userData.initialBMI}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Current BMI: ${userData.currentBMI}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color(0xFF1B5E20)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "BMI Category: ${userData.bmiCategory}",
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Edit")
            }

        }
    }
}
