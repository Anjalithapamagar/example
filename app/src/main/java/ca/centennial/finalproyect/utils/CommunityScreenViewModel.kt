package ca.centennial.finalproyect.utils

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.centennial.finalproyect.model.Post
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Date

sealed class CommunityScreenState {
    object Loading: CommunityScreenState()
    data class Loaded(
        val photoURL: String,
        val posts: List<Post>,
        val likes: Int
    ) : CommunityScreenState()

}

class CommunityScreenViewModel : ViewModel() {

    private val mutableState = MutableStateFlow<CommunityScreenState>(
        CommunityScreenState.Loading
    )

    val state = mutableState.asStateFlow()

    private val textState = MutableStateFlow("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentUser = Firebase.auth.currentUser
            if (currentUser != null) {
                observePosts(currentUser)
            } else {
                mutableState.emit(
                    CommunityScreenState.Loading
                )
            }
        }
    }

    private suspend fun observePosts(currentUser: FirebaseUser) {
        observerPosts().map { posts ->
            CommunityScreenState.Loaded(
                photoURL = "",
                posts = posts,
                likes = 0
            )
        }.collect {
            mutableState.emit(it)
        }
    }

    private fun observerPosts(): Flow <List<Post>> {
        return callbackFlow {
            val listener =
                Firebase.firestore.collection("posts").addSnapshotListener { value, error ->
                    if (error != null) {
                        close(error)
                    } else if (value != null) {
                        val posts = value.map { doc ->
                            Post(
                                author = doc.getString("author").orEmpty(),
                                content = doc.getString("content").orEmpty(),
                                timeStamp = doc.getDate("date_posted") ?: Date(),
                                likes = (doc.get("likes") as? Long)?.toInt() ?: 0
                            )
                        }.sortedByDescending { it.timeStamp }
                        trySend(posts)
                    }
                }
            awaitClose {
                listener.remove()
            }
        }
    }

    fun onTextChanged(text: String) {
        viewModelScope.launch {
            textState.emit(text)
        }
    }

    fun onSendClick(context: Context) {
        viewModelScope.launch(Dispatchers.IO){

            val postText = textState.value
            val currentUser = Firebase.auth.currentUser
            val profileViewModel = ProfileViewModel()

            if (currentUser != null) {

                profileViewModel.getUserData(currentUser.uid, context) { user ->

                    val authorName = "${user.firstName} ${user.lastName}"

                    Firebase.firestore.collection("posts").add(
                        hashMapOf(
                            "author" to authorName,
                            "content" to postText,
                            "date_posted" to Date()
                        )
                    ).addOnSuccessListener { documentReference ->
                        FoodNotificationService(context).showBasicNotification(authorName, postText)
                    }
                }
            } else {
                "Tried to post without a signed in user"
            }
        }
    }

    fun incrementLikes(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            val postRef = Firebase.firestore.collection("posts").document(post.author)
            Firebase.firestore.runTransaction { transaction ->
                val snapshot = transaction.get(postRef)
                val currentLikes = snapshot.getLong("likes") ?: 0
                transaction.update(postRef, "likes", currentLikes + 1)
            }.addOnSuccessListener {
                // Likes incremented successfully
            }.addOnFailureListener { exception ->
                // Handle failure
            }
        }
    }


    fun deletePostIfAuthorMatches(context: Context, post: Post) {
        val currentUser = Firebase.auth.currentUser
        val profileViewModel = ProfileViewModel()

        if (currentUser != null) {

            profileViewModel.getUserData(currentUser.uid, context) { user ->

                val authorFullName = "${user.firstName} ${user.lastName}"

                if (post.author == authorFullName) {
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.apply {
                        setTitle("Confirm Deletion")
                        setMessage("Are you sure you want to delete this post?")
                        setPositiveButton("Yes") { _, _ ->
                            Firebase.firestore.collection("posts")
                                .whereEqualTo("author", authorFullName)
                                .whereEqualTo("content", post.content)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        document.reference.delete()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    // Handle errors
                                }
                        }
                        setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        show()
                    }
                } else {
                    val alertDialogBuilder = AlertDialog.Builder(context)
                    alertDialogBuilder.apply {
                        setTitle("Error")
                        setMessage("You are not authorized to delete this post.")
                        setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                        }
                        show()
                    }
                }
            }
        }
    }


}
