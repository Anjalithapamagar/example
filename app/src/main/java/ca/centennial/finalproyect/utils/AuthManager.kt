package ca.centennial.finalproyect.utils

import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

sealed class AuthRes<out T> {
    data class Success<T>(val data: T): AuthRes<T>()
    data class Error(val errorMessage: String): AuthRes<Nothing>()
}

class AuthManager(private val context: Context) {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private val signInClient = Identity.getSignInClient(context)

    suspend fun signInAnonymously(): AuthRes<FirebaseUser> {
        return try {
            val result = auth.signInAnonymously().await()
            AuthRes.Success(result.user ?: throw Exception("failed to login"))
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "failed to login")
        }
    }

    suspend fun createUserWithEmailAndPassword(email: String,
                                               password: String,
                                               firstName: String,
                                               lastName: String,
                                               dateOfBirth: String,
                                               gender: String,
                                               height: Double,
                                               weight: Double,
                                               initialBMI: Double,
                                               currentBMI: Double,
                                               bmiCategory: String): AuthRes<FirebaseUser?> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid
            if (uid != null) {
                val db = FirebaseFirestore.getInstance()
                val userData = hashMapOf(
                    "uid" to uid,
                    "firstName" to firstName,
                    "lastName" to lastName,
                    "email" to email,
                    "dateOfBirth" to dateOfBirth,
                    "gender" to gender,
                    "height" to height,
                    "weight" to weight,
                    "initialBMI" to initialBMI,
                    "currentBMI" to currentBMI,
                    "bmiCategory" to bmiCategory
                )
                db.collection("users").document(uid).set(userData).await()
            }
            AuthRes.Success(authResult.user)
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Error creating user")
        }
    }

    private suspend fun saveAdditionalUserData(user: FirebaseUser?, birthDate: String, height: Double, weight: Double) {
        // Aquí puedes implementar la lógica para guardar los datos adicionales en una base de datos Firebase
        // Por ejemplo, utilizando Firestore o Realtime Database
        // En este ejemplo, solo se imprime la información, pero deberías adaptarlo a tu estructura de base de datos
        user?.let {
            val userId = it.uid
            println("Guardando datos adicionales para el usuario con ID: $userId")
            println("Fecha de nacimiento: $birthDate")
            println("Estatura: $height")
            println("Peso: $weight")
            // Aquí deberías guardar los datos en tu base de datos Firebase
        }
    }

    suspend fun signInWithEmailAndPassword(email: String, password: String): AuthRes<FirebaseUser?> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            AuthRes.Success(authResult.user)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Error to login")
        }
    }

    suspend fun resetPassword(email: String): AuthRes<Unit> {
        return try {
            auth.sendPasswordResetEmail(email).await()
            AuthRes.Success(Unit)
        } catch(e: Exception) {
            AuthRes.Error(e.message ?: "Password reset error")
        }
    }

    fun signOut() {
        auth.signOut()
        signInClient.signOut()
    }

    fun getCurrentUser(): FirebaseUser?{
        return auth.currentUser
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            //.requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    fun handleSignInResult(task: Task<GoogleSignInAccount>): AuthRes<GoogleSignInAccount>? {
        return try {
            val account = task.getResult(ApiException::class.java)
            AuthRes.Success(account)
        } catch (e: ApiException) {
            AuthRes.Error(e.message ?: "Google sign-in failed.")
        }
    }

    suspend fun signInWithGoogleCredential(credential: AuthCredential): AuthRes<FirebaseUser>? {
        return try {
            val firebaseUser = auth.signInWithCredential(credential).await()
            firebaseUser.user?.let {
                AuthRes.Success(it)
            } ?: throw Exception("Sign in with Google failed.")
        } catch (e: Exception) {
            AuthRes.Error(e.message ?: "Sign in with Google failed.")
        }
    }

    fun signInWithGoogle(googleSignInLauncher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }
}
