package ca.centennial.finalproyect.ui.screens.auth


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.centennial.finalproyect.R


import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.AuthRes


import kotlinx.coroutines.launch


@Composable
fun  ForgotPasswordScreen(analytics: AnalyticsManager, auth: AuthManager, navigation: NavController) {
    analytics.logScreenView(screenName = Routes.ForgotPassword.route)


    val context = LocalContext.current
    var email by remember { mutableStateOf("") }


    val scope = rememberCoroutineScope()


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.nutrimatelogo),
            contentDescription = "NutriMate",
            modifier = Modifier
                .size(200.dp)
                .padding(bottom = 20.dp)
        )
        Text(
            text = stringResource(R.string.forgot_pass),
            style = TextStyle(fontSize = 20.sp, color = Color(0xFF2E7D32)),
            modifier = Modifier.padding(bottom = 20.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = stringResource(R.string.email)) },
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email Icon"
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF2E7D32),
                unfocusedBorderColor = Color(0xFF388E3C)
            ),
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                scope.launch {
                    when (val res = auth.resetPassword(email)) {
                        is AuthRes.Success -> {
                            analytics.logButtonClicked(buttonName = "Reset password $email")
                            Toast.makeText(context, R.string.email_sent, Toast.LENGTH_SHORT).show()
                            navigation.navigate(Routes.Login.route)
                        }
                        is AuthRes.Error -> {
                            analytics.logError(error = "Reset password error $email : ${res.errorMessage}")
                            Toast.makeText(context, R.string.error_sending_email, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .width(300.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF2E7D32))
        ) {
            Text(text = stringResource(R.string.recover_password))
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Back to login",
            style = TextStyle(color = Color(0xFF2E7D32), textDecoration = TextDecoration.Underline),
            modifier = Modifier
                .clickable {
                    navigation.navigate(Routes.Login.route)
                }
        )
    }
}

