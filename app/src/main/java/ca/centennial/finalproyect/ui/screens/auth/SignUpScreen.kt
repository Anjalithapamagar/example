package ca.centennial.finalproyect.ui.screens.auth


import android.app.AlertDialog
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.AuthRes
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


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
                contentDescription = "NutriMate",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 20.dp)
            )
            Text(
                text = "Create an Account",
                style = TextStyle(fontSize = 20.sp, color = Color(0xFF2E7D32))
            )
            Spacer(modifier = Modifier.height(20.dp))


            // Abc Section (Email and Password)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.email)) },
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { email = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    label = { Text(text = stringResource(R.string.password)) },
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    onValueChange = { password = it }
                )
            }


            Spacer(modifier = Modifier.height(40.dp))


            // Basic Info Section (First Name, Last Name, Date of Birth, Gender)


            Text(
                text = "Basic Info Section",
                style = TextStyle(fontSize = 16.sp, color = Color(0xFF2E7D32))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = { Text("First Name") },
                    value = firstName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { firstName = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    label = { Text("Last Name") },
                    value = lastName,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    onValueChange = { lastName = it }
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Date of Birth
                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = { /* Ignored */ },
                    label = { Text("Date of Birth") },
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, contentDescription = "Select Date",
                            modifier = Modifier.clickable {
                                showDatePicker(context) { selectedDate ->
                                    dateOfBirth = selectedDate
                                }
                            })
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                // Gender
                OutlinedTextField(
                    label = { Text("Gender") },
                    value = gender,
                    onValueChange = { /* Ignored */ },
                    readOnly = true,
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Expand Gender Menu",
                            modifier = Modifier.clickable {
                                showGenderSelectionDialog(context) { selectedGender ->
                                    gender = selectedGender
                                }
                            }
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Body Measurements",
                style = TextStyle(fontSize = 16.sp, color = Color(0xFF2E7D32))
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Body Info Section (Height and Weight)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = { Text("Height (Meters)") },
                    value = height.toString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newValue ->
                        height = newValue.toDoubleOrNull() ?: 0.0
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    label = { Text("Weight (Kilogram)") },
                    value = weight.toString(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newValue ->
                        weight = newValue.toDoubleOrNull() ?: 0.0
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
                    Text(text = stringResource(R.string.signup))
                }
            }


            Spacer(modifier = Modifier.height(40.dp))


            // Already have an account? ClickableText
            ClickableText(
                text = AnnotatedString(stringResource(R.string.do_you_already_have_account)),
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

fun showGenderSelectionDialog(context: Context, onGenderSelected: (String) -> Unit) {
    val genderOptions = listOf("Male", "Female", "Others")
    val dialog = AlertDialog.Builder(context)
        .setTitle("Select Gender")
        .setItems(genderOptions.toTypedArray()) { _, which ->
            val selectedGender = genderOptions[which]
            onGenderSelected(selectedGender)
        }
        .setCancelable(true)
        .create()
    dialog.show()
}

fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = formatDateString(selectedYear, selectedMonth, selectedDay)
            onDateSelected(selectedDate)
        }, year, month, day)

    datePickerDialog.show()
}

fun formatDateString(year: Int, month: Int, day: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day)
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(calendar.time)
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
    auth: AuthManager,
    analytics: AnalyticsManager,
    context: Context,
    navigation: NavController
) {
    if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() &&
        lastName.isNotEmpty() && dateOfBirth.isNotEmpty() && gender.isNotEmpty() &&
        height > 0 && weight > 0
    ) {
        val (calculatedBMI, bmiCategory) = calculateBMI(height, weight)

        when (val result = auth.createUserWithEmailAndPassword(
            email, password, firstName, lastName, dateOfBirth, gender,
            height, weight, calculatedBMI, calculatedBMI, bmiCategory
        )) {
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
                Toast.makeText(
                    context,
                    "Error SignUp: ${result.errorMessage}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    } else {
        Toast.makeText(
            context,
            context.getString(R.string.there_are_empty_fields), Toast.LENGTH_SHORT
        ).show()
    }
}


private fun calculateBMI(height: Double, weight: Double): Pair<Double, String> {
    val heightInMeters = height
    val bmi = weight / (heightInMeters * heightInMeters)

    val roundedBMI = "%.2f".format(bmi).toDouble()

    val category = when {
        bmi < 18.5 -> "Underweight"
        bmi in 18.5..24.9 -> "Healthy Weight"
        bmi in 25.0..29.9 -> "Overweight"
        else -> "Obesity"
    }

    return Pair(roundedBMI, category)
}



