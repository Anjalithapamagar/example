package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.ui.theme.PurpleGrey40
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(authManager: AuthManager, navigation: NavController) {

    val context = LocalContext.current

    val profileViewModel = remember { ProfileViewModel() }

    var showDialog by remember { mutableStateOf(false) }
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

    //ERROR NAVIGATING BACK TO LOGIN SCREEN
    val onLogoutConfirmed: () -> Unit = {
        authManager.signOut()
        navigation.navigate(Routes.Login.route){
            popUpTo(Routes.Home.route) {
                inclusive = true
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
            Text(
                text = "${userData.firstName} ${userData.lastName}",
                color = Color(0xFF1B5E20),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Image(
                painter = painterResource(id = R.drawable.user_profile),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Basic Info Section (First Name, Last Name, Date of Birth, Gender)

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
                    text = "Date of Birth: ${userData.dateOfBirth} (${userAge})",
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
                    imageVector = Icons.Default.ThumbUp,
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
                onClick = {
                    showDialog = true
                },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF2E7D32)
                )
            ) {
                Icon(Icons.Outlined.ExitToApp, contentDescription = stringResource(R.string.sign_off))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Logout")
            }
            if (showDialog) {
                LogoutDialog(onConfirmLogout = {
                    onLogoutConfirmed()
                    showDialog = false
                }, onDismiss = { showDialog = false })
            }
        }
    }
}

@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Log Out", color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold) },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            Button(
                onClick = onConfirmLogout,
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF2E7D32)
                )
            ) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    Color(0xFF2E7D32)
                )
            ) {
                Text("Cancel")
            }
        }
    )
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