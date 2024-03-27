import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

// Data model for posts
data class Post(val id: Int, val content: String, var likes: Int = 0, var comments: Int = 0)

// Simulated repository for fetching posts
object FakePostsRepository {
    fun getPosts(): List<Post> {
        return listOf(
            Post(1, "First post"),
            Post(2, "Second post"),
            Post(3, "Third post")
        )
    }
}

@Composable
fun CommunityPosts() {
    val posts by remember { mutableStateOf(FakePostsRepository.getPosts()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        posts.forEach { post ->
            PostItem(post = post)
            Spacer(modifier = Modifier.height(8.dp))
        }
        AddPostButton(onAddPost = { content ->
            val newPost = Post(posts.size + 1, content)
            posts.toMutableList().apply { add(newPost) }
        })
    }
}

@Composable
fun PostItem(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { post.likes++ }) {
                    Icon(Icons.Default.Favorite, contentDescription = "Like")
                }
                Text(text = "${post.likes} Likes")
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = { post.comments++ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Comment")
                }
                Text(text = "${post.comments} Comments")
            }
        }
    }
}

@Composable
fun AddPostButton(onAddPost: (String) -> Unit) {
    var newPostText by remember { mutableStateOf("") }

    FloatingActionButton(
        onClick = {
            if (newPostText.isNotBlank()) {
                onAddPost(newPostText)
                newPostText = ""
            }
        },
        modifier = Modifier
            .padding(16.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add")
    }

    Dialog(onDismissRequest = { /* Dismiss the dialog */ }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            TextField(
                value = newPostText,
                onValueChange = { newPostText = it },
                label = { Text("Enter your new post") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (newPostText.isNotBlank()) {
                        onAddPost(newPostText)
                        newPostText = ""
                    }
                })
            )
        }
    }
}

@Preview
@Composable
fun PreviewCommunityPosts() {
    CommunityPosts()
}

@Preview
@Composable
fun PreviewPostItem() {
    PostItem(Post(1, "Sample Post"))
}

@Preview
@Composable
fun PreviewAddPostButton() {
    AddPostButton(onAddPost = { })
}
