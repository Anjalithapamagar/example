package ca.centennial.finalproyect.utils

import android.widget.Toast
import androidx.lifecycle.ViewModel
import ca.centennial.finalproyect.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.content.Context
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(): ViewModel() {

    fun saveUserData(
        userData: User,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("users")
            .document(userData.uid)

        try {
            fireStoreRef.set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "Saved Successfully", Toast.LENGTH_SHORT).show()
                }

        } catch (e:Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun getUserData(
        userID: String,
        context: Context,
        data: (User) -> Unit
    ) = CoroutineScope(Dispatchers.IO).launch {

        val fireStoreRef = Firebase.firestore
            .collection("users")
            .document(userID)

        try {
            fireStoreRef.get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val userData = it.toObject<User>()!!
                        data(userData)
                    } else {
                        Toast.makeText(context, "No User Data Found", Toast.LENGTH_SHORT).show()
                    }
                }

        } catch (e:Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

}