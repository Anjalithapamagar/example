package ca.centennial.finalproyect.ui.screens.auth

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.crashlytics.FirebaseCrashlytics

import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.ui.theme.Purple40
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.AuthRes

import kotlinx.coroutines.launch
import java.lang.RuntimeException
/*
ES
Argumentos:
@analytics: Un objeto de tipo AnalyticsManager para realizar seguimiento y registro de análisis.
@auth: Un objeto de tipo AuthManager para gestionar la autenticación del usuario.
@navigation: Un objeto de tipo NavController para controlar la navegación entre pantallas.
EN
Arguments:
@analytics: An AnalyticsManager object for tracking and logging analytics.
@auth: An object of type AuthManager to manage user authentication.
@navigation: An object of type NavController to control navigation between screens.
 */
@Composable
fun LoginScreen(analytics: AnalyticsManager, auth: AuthManager, navigation: NavController) {
    analytics.logScreenView(screenName = Routes.Login.route)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()) { result ->
        when(val account = auth.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(result.data))) {
            is AuthRes.Success -> {
                val credential = GoogleAuthProvider.getCredential(account?.data?.idToken, null)
                scope.launch {
                    val fireUser = auth.signInWithGoogleCredential(credential)
                    if (fireUser != null){
                        Toast.makeText(context, R.string.welcome, Toast.LENGTH_SHORT).show()
                        navigation.navigate(Routes.Home.route){
                            popUpTo(Routes.Login.route){
                                inclusive = true
                            }
                        }
                    }
                }
            }
            is AuthRes.Error -> {
                analytics.logError("Error SignIn: ${account.errorMessage}")
                Toast.makeText(context, "Error: ${account.errorMessage}", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(context, R.string.unknown_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString(stringResource(R.string.you_do_dnot_dhave_daccount)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(40.dp),
            onClick = {
                navigation.navigate(Routes.SignUp.route)
                analytics.logButtonClicked("Click: You do not have an account? Sign up")
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple40
            )
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_firebase),
            contentDescription = "Firebase",
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "NUTRI-MATE APP",
            style = TextStyle(fontSize = 30.sp))
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            label = { Text(text = stringResource(R.string.email)) },
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = { email = it })
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            label = { Text(text = stringResource(R.string.password)) },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it })
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    scope.launch {
                        emailPassSignIn(email, password, auth, analytics, context, navigation)
                    }
                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = stringResource(R.string.login).uppercase())
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        ClickableText(
            text = AnnotatedString(stringResource(R.string.forget_pass)),
            onClick = {
                navigation.navigate(Routes.ForgotPassword.route) {
                    popUpTo(Routes.Login.route) { inclusive = true }
                }
                analytics.logButtonClicked("Click: Did you forget your password?")
            },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = Purple40
            )
        )
        /* Spacer(modifier = Modifier.height(25.dp))
         Text(text = "-------- o --------", style = TextStyle(color = Color.Gray))
         Spacer(modifier = Modifier.height(25.dp))
         SocialMediaButton(
             onClick = {
                 scope.launch{
                     incognitoSignIn(auth, analytics, context, navigation)
                 }
             },
             text = stringResource(R.string.continue_as_guest),
             icon = R.drawable.ic_incognito,
             color = Color(0xFF363636)
         )*/
        Spacer(modifier = Modifier.height(15.dp))
        SocialMediaButton(
            onClick = {
                auth.signInWithGoogle(googleSignInLauncher)
            },
            text = stringResource(R.string.continue_with_google),
            icon = R.drawable.ic_google,
            color = Color(0xFFF1F1F1)
        )
        Spacer(modifier = Modifier.height(25.dp))
        ClickableText(
            text = AnnotatedString(stringResource(R.string.force_crashlytics)),
            onClick = {
                val crashlytics = FirebaseCrashlytics.getInstance()
                crashlytics.setCustomKey("testKey", "key test value")
                crashlytics.log("Custom message from a log")

                throw RuntimeException("Forced error from LoginScreen")
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

private suspend fun incognitoSignIn(auth: AuthManager, analytics: AnalyticsManager, context: Context, navigation: NavController) {
    when(val result = auth.signInAnonymously()) {
        is AuthRes.Success -> {
            analytics.logButtonClicked("Click: Continue as guest")
            navigation.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) {
                    inclusive = true
                }
            }
        }
        is AuthRes.Error -> {
            analytics.logError("Incognito SignIn Error: ${result.errorMessage}")
        }
    }
}

private suspend fun emailPassSignIn(email: String, password: String, auth: AuthManager, analytics: AnalyticsManager, context: Context, navigation: NavController) {
    if(email.isNotEmpty() && password.isNotEmpty()) {
        when (val result = auth.signInWithEmailAndPassword(email, password)) {
            is AuthRes.Success -> {
                analytics.logButtonClicked("Click: Iniciar sesión correo & contraseña")
                navigation.navigate(Routes.Home.route) {
                    popUpTo(Routes.Login.route) {
                        inclusive = true
                    }
                }
            }

            is AuthRes.Error -> {
                analytics.logButtonClicked("Error SignUp: ${result.errorMessage}")
                Toast.makeText(context, "Error SignUp: ${result.errorMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Toast.makeText(context, "There are empty fields", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SocialMediaButton(onClick: () -> Unit, text: String, icon: Int, color: Color, ) {
    var click by remember { mutableStateOf(false) }
    Surface(
        onClick = onClick,
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp)
            .clickable { click = !click },
        shape = RoundedCornerShape(50),
        border = BorderStroke(width = 1.dp, color = if(icon == R.drawable.ic_incognito) color else Color.Gray),
        color = color
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                modifier = Modifier.size(24.dp),
                contentDescription = text,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "$text", color = if(icon == R.drawable.ic_incognito) Color.White else Color.Black)
            click = true
        }
    }
}