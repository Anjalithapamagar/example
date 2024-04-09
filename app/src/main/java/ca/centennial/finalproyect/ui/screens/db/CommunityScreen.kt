import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.model.Post
import ca.centennial.finalproyect.utils.AuthManager
import coil.compose.AsyncImage
import coil.request.ImageRequest
import android.text.format.DateUtils
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.utils.CommunityScreenState
import ca.centennial.finalproyect.utils.CommunityScreenViewModel
import ca.centennial.finalproyect.utils.ProfileViewModel
import java.util.Date

@Composable
fun CommunityScreen(auth: AuthManager, context: Context) {

    val communityViewModel = viewModel<CommunityScreenViewModel>()
    val state by communityViewModel.state.collectAsState()
    when (state) {

        is CommunityScreenState.Loaded -> {
            val loaded = state as CommunityScreenState.Loaded
            CommunityScreenContents(
                auth,
                posts = loaded.posts,
                onTextChange = {
                    communityViewModel.onTextChanged(it)
                },
                onSendClick = {
                    communityViewModel.onSendClick(context)
                },
                onDeleteClicked = { post ->
                    communityViewModel.deletePostIfAuthorMatches(context, post)
                },
                onLikeClicked = { post ->
                    communityViewModel.incrementLikes(post)
                }
            )
        }
        CommunityScreenState.Loading -> LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CommunityScreenContents(
    auth: AuthManager,
    posts :List<Post>,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    onDeleteClicked: (Post) -> Unit,
    onLikeClicked: (Post) -> Unit) {
    Box(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                PostBar(
                    auth = auth,
                    onTextChange = onTextChange,
                    onSendClick = onSendClick
                )
            }
            items(posts) { post ->
                Spacer(Modifier.height(8.dp))
                PostCard(auth, post,onDeleteClicked, onLikeClicked)
                Spacer(Modifier.height(8.dp))

            }
        }
    }
}

@Composable
fun PostCard(auth: AuthManager, post: Post, onDeleteClicked: (Post) -> Unit, onLikeClicked: (Post) -> Unit) {
    val user = auth.getCurrentUser()

//    val context = LocalContext.current
//
//    val communityViewModel = remember { CommunityScreenViewModel() }
//
//    var postData by remember { mutableStateOf(User()) }
//
//    LaunchedEffect(key1 = true) {
//        auth.getCurrentUser()?.`uid?.let { post ->
//            communityViewModel.getPostData(userId, context) { user ->
//                userData = user
//            }
//        }
//    }

    Surface {
        Column {
            Row (
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically) {
                if (user?.photoUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image",
                        placeholder = painterResource(id = R.drawable.user_profile),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.user_profile),
                        contentDescription = "Default profile photo",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }

                Column (
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)) {
                    Text(post.author,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    val today = remember {
                        Date()
                    }
                    Text(dateLabel(timeStamp = post.timeStamp, today = today),
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.66f
                        )
                    )
                }
                EditDeleteIconButton(
                    onEditClicked = {
                        // TODO
                    },
                    onDeleteClicked = { onDeleteClicked(post) },
                    post = post
                )

            }

            Text(post.content,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))

            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = {
                    onLikeClicked(post)
                }) {
                    Icon(Icons.Outlined.ThumbUp, contentDescription = "like")
                }
                Text(text = "Likes")
                Spacer(modifier = Modifier.width(16.dp))
                IconButton(onClick = {
                    // TODO
                }) {
                    Icon(Icons.Filled.Email, contentDescription = "comment")
                }
                Text(text = "Comment")
            }
        }
    }
}
@Composable
fun EditDeleteIconButton(onEditClicked: () -> Unit, onDeleteClicked: () -> Unit, post: Post) {
    var showDialog by remember { mutableStateOf(false) }

    IconButton(onClick = { showDialog = true }) {
        Icon(Icons.Rounded.MoreVert, contentDescription = "menu")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(text = "Options")
            },
            confirmButton = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = {
                        onEditClicked()
                        showDialog = false
                    }) {
                        Text("Edit")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = {
                        onDeleteClicked()
                        showDialog = false
                    }) {
                        Text("Delete")
                    }
                }
            }
        )
    }
}


@Composable
private fun dateLabel(timeStamp: Date, today: Date): String {
    return if (today.time - timeStamp.time < 2 * DateUtils.MINUTE_IN_MILLIS) {
        stringResource(R.string.just_now)
    } else {
        DateUtils.getRelativeTimeSpanString(timeStamp.time,
            today.time,
            0,
            DateUtils.FORMAT_SHOW_WEEKDAY).toString()
    }
}

@Composable
private fun PostBar(
    auth: AuthManager,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    val context = LocalContext.current

    val user = auth.getCurrentUser()

    val profileViewModel = remember { ProfileViewModel() }

    var userData by remember { mutableStateOf(User()) }

    LaunchedEffect(key1 = true) {
        auth.getCurrentUser()?.uid?.let { userId ->
            profileViewModel.getUserData(userId, context) { user ->
                userData = user
            }
        }
    }

    Divider(
        color = Color.Black,
        thickness = 1.dp,
        modifier = Modifier.fillMaxWidth()
    )
    Surface {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (user?.photoUrl != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(user.photoUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Image",
                        placeholder = painterResource(id = R.drawable.user_profile),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(40.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.user_profile),
                        contentDescription = "Default profile photo",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(Modifier.width(8.dp))

                var text by remember {
                    mutableStateOf("")
                }
                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    value = text, onValueChange = {
                        text = it
                        onTextChange(it)
                    },
                    placeholder = {
                        if (user != null) {
                            Text(text = "What's on your mind, ${userData.firstName}?")
                        }
                    }
                )
                IconButton(
                    onClick = {
                        onSendClick()
                        text = ""
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.Black
                    )
                }
            }
            Divider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}