package ca.centennial.finalproyect.utils

import android.content.Context
import ca.centennial.finalproyect.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreManager(context: Context) {

    private val firestore = FirebaseFirestore.getInstance()

    private val auth = AuthManager(context)

    var userId = auth.getCurrentUser()?.uid

    val db = Firebase.firestore

    val postsCollection = db.collection("posts")

    suspend fun fetchPostsFromFirestore(): List<Post> {
        val postsList = mutableListOf<Post>()
        try {
            val querySnapshot = postsCollection.get().await()
            for (document in querySnapshot.documents) {
                val post = document.toObject(Post::class.java)
                post?.let {
                    postsList.add(it)
                }
            }
        } catch (e: Exception) {
            // Handle any errors
            e.printStackTrace()
        }
        return postsList
    }

//    suspend fun addNote(note: Note) {
//        note.userId = userId.toString()
//        firestore.collection("notes").add(note).await()
//    }
//
//    suspend fun updateNote(note: Note) {
//        val noteRef = note.id?.let { firestore.collection("notes").document(it) }
//        noteRef?.set(note)?.await()
//    }
//
//    suspend fun deleteNote(noteId: String) {
//        val noteRef = firestore.collection("notes").document(noteId)
//        noteRef.delete().await()
//    }
//
//    fun getNotesFlow(): Flow<List<Note>> = callbackFlow {
//        val notesRef = firestore.collection("notes")
//            .whereEqualTo("userId", userId).orderBy("title")
//
//        val subscription = notesRef.addSnapshotListener { snapshot, _ ->
//            snapshot?.let { querySnapshot ->
//                val notes = mutableListOf<Note>()
//                for (document in querySnapshot.documents) {
//                    val note = document.toObject(Note::class.java)
//                    note?.id = document.id
//                    note?.let { notes.add(it) }
//                }
//                trySend(notes).isSuccess
//            }
//        }
//        awaitClose { subscription.remove() }
//    }
}