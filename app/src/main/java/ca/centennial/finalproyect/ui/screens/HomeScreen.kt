package ca.centennial.finalproyect.ui.screens

import CommunityPosts
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ca.centennial.finalproyect.R
import ca.centennial.finalproyect.model.User
import ca.centennial.finalproyect.ui.navigation.Routes
import ca.centennial.finalproyect.ui.screens.db.TrackerScreen
import ca.centennial.finalproyect.ui.screens.db.DailyMealPlanScreen
import ca.centennial.finalproyect.ui.screens.db.ProfileScreen
import ca.centennial.finalproyect.ui.screens.storage.CloudStorageScreen
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.CloudStorageManager
import ca.centennial.finalproyect.utils.FirestoreManager
import ca.centennial.finalproyect.utils.ProfileViewModel
import ca.centennial.finalproyect.utils.RealtimeManager
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig

private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig
private var welcomeMessage by mutableStateOf("Welcome")
private var isButtonVisible by mutableStateOf(true)

val WELCOME_MESSAGE_KEY = "welcome_message"
val IS_BUTTON_VISIBLE_KEY = "is_button_visible"

@Composable
fun HomeScreen(analytics: AnalyticsManager, auth: AuthManager, navigation: NavController, profileViewModel: ProfileViewModel) {
    analytics.logScreenView(screenName = Routes.Home.route)
    val navController = rememberNavController()

    initRemoteConfig()

    val user = auth.getCurrentUser()

    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val profileViewModel = remember { ProfileViewModel() }
    var userData by remember { mutableStateOf(User()) }

    LaunchedEffect(key1 = true) {
        auth.getCurrentUser()?.uid?.let { userId ->
            profileViewModel.getUserData(userId, context) { user ->
                userData = user
            }
        }
    }

    val onLogoutConfirmed: () -> Unit = {
        auth.signOut()
        navigation.navigate(Routes.Login.route) {
            popUpTo(Routes.Home.route) {
                inclusive = true
            }
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(user?.photoUrl != null) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(user?.photoUrl)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "Image",
                                placeholder = painterResource(id = R.drawable.profileplaceholder),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(40.dp))
                        } else {
                            Image(
                                painter = painterResource(R.drawable.profileplaceholder),
                                contentDescription = "Default profile photo",
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = if (userData.firstName.isNotEmpty() && userData.lastName.isNotEmpty()) {
                                    "Hi ${userData.firstName} ${userData.lastName}"
                                } else {
                                    welcomeMessage
                                },
                                fontSize = 20.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = if(!user?.email.isNullOrEmpty()) "${user?.email}" else "Anonymous",
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis)
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                actions = {
                    IconButton(
                        onClick = {
                            val crashlytics = FirebaseCrashlytics.getInstance()
                            crashlytics.setCustomKey("Home Key test", "Value to send")
                            crashlytics.log("Log message from HomeScreen")
                            crashlytics.setUserId(user?.uid ?: "No Id Found")
                            crashlytics.setCustomKeys {
                                key("str", "hello")
                                key("bool", true)
                                key("int", 5)
                                key("long", 5.8)
                                key("float", 1.0f)
                                key("double", 1.0)
                            }
                            throw RuntimeException("Forced error from HomeScreen")
                        },
                        modifier = Modifier.alpha(if (isButtonVisible) 1f else 0f)
                    ) {
                        Icon(Icons.Default.Warning , contentDescription = stringResource(R.string.force_error))
                    }
                    IconButton(
                        onClick = {
                            showDialog = true
                        }
                    ) {
                        Icon(Icons.Outlined.ExitToApp, contentDescription = stringResource(R.string.sign_off))
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ){ contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            if (showDialog) {
                LogoutDialog(onConfirmLogout = {
                    onLogoutConfirmed()
                    showDialog = false
                }, onDismiss = { showDialog = false })
            }
            BottomNavGraph(navController = navController, context = context, authManager = auth)
        }
    }
}

fun initRemoteConfig() {
    mFirebaseRemoteConfig = Firebase.remoteConfig
    val configSettings: FirebaseRemoteConfigSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(3600)
        .build()
    mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
    mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    mFirebaseRemoteConfig.addOnConfigUpdateListener(object: ConfigUpdateListener {
        override fun onUpdate(configUpdate: ConfigUpdate) {
            Log.d("HomeScreen", "Updated keys: " + configUpdate.updatedKeys)
            if(configUpdate.updatedKeys.contains(IS_BUTTON_VISIBLE_KEY) || configUpdate.updatedKeys.contains(WELCOME_MESSAGE_KEY)) {
                mFirebaseRemoteConfig.activate().addOnCompleteListener {
                    displayWelcomeMessage()
                }
            }
        }
        override fun onError(error: FirebaseRemoteConfigException) {
        }
    })

    fetchWelcome()
}

fun fetchWelcome() {
    mFirebaseRemoteConfig.fetchAndActivate()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val updated = task.result
                println("Updated parameters:: $updated")
            } else {
                println("Fetch failed")
            }
        }
}

fun displayWelcomeMessage() {
    welcomeMessage = mFirebaseRemoteConfig[WELCOME_MESSAGE_KEY].asString()
    isButtonVisible = mFirebaseRemoteConfig[IS_BUTTON_VISIBLE_KEY].asBoolean()
}

@Composable
fun LogoutDialog(onConfirmLogout: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Sign off") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            Button(
                onClick = onConfirmLogout
            ) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomNavScreen.Home,
        BottomNavScreen.Tracker,
        BottomNavScreen.Community,
        BottomNavScreen.Scan,
        BottomNavScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar {
        screens.forEach { screens ->
            if (currentDestination != null) {
                AddItem(
                    screens = screens,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(screens: BottomNavScreen, currentDestination: NavDestination, navController: NavHostController) {
    NavigationBarItem(
        label = { Text(text = screens.title) },
        icon = { Icon(imageVector = screens.icon, contentDescription = "Icons") },
        selected = currentDestination.hierarchy?.any {
            it.route == screens.route
        } == true,
        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun BottomNavGraph(navController: NavHostController, context: Context, authManager: AuthManager) {
    val realtime = RealtimeManager(context)
    val firestore = FirestoreManager(context)
    val storage = CloudStorageManager(context)
    NavHost(navController = navController, startDestination = BottomNavScreen.Home.route) {
        composable(route = BottomNavScreen.Home.route) {
            DailyMealPlanScreen(realtime = realtime, authManager = authManager)
        }
        composable(route = BottomNavScreen.Tracker.route) {
            TrackerScreen(firestore = firestore)
        }
        composable(route = BottomNavScreen.Scan.route) {
            CloudStorageScreen(storage = storage)
        }
        composable(route = BottomNavScreen.Profile.route) {
            ProfileScreen(profileViewModel = ProfileViewModel(), authManager = authManager)
        }
        composable(route = BottomNavScreen.Community.route) {
            CommunityPosts()
        }
    }
}

sealed class BottomNavScreen(val route: String, val title: String, val icon: ImageVector) {
    object Community : BottomNavScreen(
        route = "community",
        title = "Community",
        icon = Icons.Default.Send
    )
    object Home : BottomNavScreen(
        route = "home",
        title = "Home",
        //icon = Icons.Default.Person
        icon = Icons.Default.Home
    )
    object Tracker : BottomNavScreen(
        route = "tracker",
        title = "Tracker",
        icon = Icons.Default.AddCircle
    )

    object Scan : BottomNavScreen(
        route = "scan",
        title = "Scan",
        icon = Icons.Default.Search
    )

    object Profile : BottomNavScreen(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

}