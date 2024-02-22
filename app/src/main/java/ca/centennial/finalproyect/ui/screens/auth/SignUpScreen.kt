package ca.centennial.finalproyect.ui.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ca.centennial.finalproyect.R

import com.google.firebase.analytics.FirebaseAnalytics
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.ui.theme.Purple40
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.AuthRes

import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(analytics: AnalyticsManager, auth: AuthManager, navigation: NavController) {
    analytics.logScreenView(screenName = Routes.SignUp.route)

    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ceate Account",
            style = TextStyle(fontSize = 40.sp, color = Purple40)
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            label = { Text(text = stringResource(R.string.email)) },
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = stringResource(R.string.password)) },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("First Name") },
            value = firstName,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { firstName = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("Last Name") },
            value = lastName,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { lastName = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("Date of Birth") },
            value = dateOfBirth,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { dateOfBirth = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("Gender") },
            value = gender,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { gender = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("Height") },
            value = height,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { height = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text("Weight") },
            value = weight,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { weight = it })

        Spacer(modifier = Modifier.height(30.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        signUp(email, password, firstName, lastName, dateOfBirth, gender, height, weight, auth, analytics, context, navigation)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.check_in))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))
        ClickableText(
            text = AnnotatedString(stringResource(R.string.do_you_already_have_account)),
            onClick = {
                navigation.popBackStack()
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple40
            )
        )
    }
}

private suspend fun signUp(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    dateOfBirth: String,
    gender: String,
    height: String,
    weight: String,
    auth: AuthManager,
    analytics: AnalyticsManager,
    context: Context,
    navigation: NavController
) {

    if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && gender.isNotEmpty() && height.isNotEmpty() && weight.isNotEmpty()) {
        when (val result = auth.createUserWithEmailAndPassword(email, password, firstName, lastName, dateOfBirth, gender, height, weight)) {
            is AuthRes.Success -> {
                analytics.logButtonClicked(FirebaseAnalytics.Event.SIGN_UP)
                Toast.makeText(
                    context,
                    context.getString(R.string.successful_registration), Toast.LENGTH_SHORT
                ).show()
                navigation.popBackStack()
            }

            is AuthRes.Error -> {
                analytics.logButtonClicked("Error SignUp: ${result.errorMessage}")
                Toast.makeText(context, "Error SignUp: ${result.errorMessage}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    } else {
        Toast.makeText(
            context,
            context.getString(R.string.there_are_empty_fields), Toast.LENGTH_SHORT
        ).show()
    }
}