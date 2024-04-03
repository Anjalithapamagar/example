package ca.centennial.finalproyect.ui.screens.auth


import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.ui.theme.PurpleGrey40
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.AuthRes
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch
import java.util.Calendar


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
    var weight by remember { mutableStateOf(0.0) }
    var height by remember { mutableStateOf(0.0) }
    val initialBMI by remember { mutableStateOf(0.0) }
    val currentBMI by remember { mutableStateOf(0.0) }
    val bmiCategory by remember { mutableStateOf("") }


    val scope = rememberCoroutineScope()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.nutrimatelogo),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Abc Section (Email and Password)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(R.string.email),
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = stringResource(R.string.password),
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Basic Info Section (First Name, Last Name, Date of Birth, Gender)

            Text(
                text = "Basic Info Section",
                style = TextStyle(fontSize = 12.sp, color = PurpleGrey40)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "First Name",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = firstName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { firstName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },
                    label = { Text(text = "Last Name",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = lastName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { lastName = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Date of Birth
                OutlinedTextField(
                    value = dateOfBirth,
                    label = { Text(text = "Date of Birth",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    onValueChange = { dateOfBirth = it },
                    readOnly = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = null,
                            modifier = Modifier.clickable {
                                showDatePicker(context) { selectedDate ->
                                    dateOfBirth = selectedDate
                                }
                            }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Gender
                OutlinedTextField(
                    label = { Text(text = "Gender",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = gender,
                    onValueChange = { gender = it },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    leadingIcon = {
                        Icon(Icons.Default.Face, contentDescription = "Expand Gender Menu")
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Body Measurements",
                style = TextStyle(fontSize = 12.sp, color = PurpleGrey40)
            )
            Spacer(modifier = Modifier.height(30.dp))

            // Body Info Section (Height and Weight)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = { Text(text = "Height",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = height.toString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newValue ->
                        height = newValue.toDoubleOrNull() ?: 0.0
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.Favorite, contentDescription = "")
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    label = { Text(text = "Weight",
                        style = TextStyle(Color(0xFF1B5E20))) },
                    value = weight.toString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newValue ->
                        weight = newValue.toDoubleOrNull() ?: 0.0
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF1B5E20), // Set the outline color when focused
                        unfocusedBorderColor = Color(0xFF388E3C) // Set the outline color when not focused
                    ),
                    leadingIcon = {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "")
                    }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Check-in Button
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        scope.launch {
                            signUp(
                                email,
                                password,
                                firstName,
                                lastName,
                                dateOfBirth,
                                gender,
                                height,
                                weight,
                                initialBMI,
                                currentBMI,
                                bmiCategory,
                                auth,
                                analytics,
                                context,
                                navigation
                            )
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        Color(0xFF2E7D32)
                    )
                ) {
                    Text(text = stringResource(R.string.check_in))
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Already have an account? ClickableText
            Text(text = AnnotatedString(stringResource(R.string.do_you_already_have_account)))
            ClickableText(
                text = AnnotatedString(stringResource(R.string.login)),
                onClick = {
                    navigation.popBackStack()
                },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = Color(0xFF2E7D32)
                )
            )
        }
    }
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)


    val datePickerDialog = DatePickerDialog(context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            onDateSelected(selectedDate)
        }, year, month, day)


    datePickerDialog.show()
}


private suspend fun signUp(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    dateOfBirth: String,
    gender: String,
    height: Double,
    weight: Double,
    initialBMI: Double,
    currentBMI: Double,
    bmiCategory: String,
    auth: AuthManager,
    analytics: AnalyticsManager,
    context: Context,
    navigation: NavController
) {


    if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && gender.isNotEmpty() && height > 0 && weight > 0) {
        when (val result = auth.createUserWithEmailAndPassword(email, password, firstName, lastName, dateOfBirth, gender, height, weight, initialBMI, currentBMI, bmiCategory)) {
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

