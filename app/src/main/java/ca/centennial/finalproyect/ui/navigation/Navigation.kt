package ca.centennial.finalproyect.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import ca.centennial.finalproyect.model.Post
import com.google.firebase.auth.FirebaseUser
import ca.centennial.finalproyect.ui.screens.HomeScreen

import ca.centennial.finalproyect.ui.screens.Screen
import ca.centennial.finalproyect.ui.screens.auth.ForgotPasswordScreen
import ca.centennial.finalproyect.ui.screens.auth.LoginScreen
import ca.centennial.finalproyect.ui.screens.auth.SignUpScreen
import ca.centennial.finalproyect.ui.screens.db.ProfileScreen
import ca.centennial.finalproyect.utils.AnalyticsManager
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.FirestoreManager

@Composable
fun Navigation(context: Context, sharedPreferences: DefaultSharedPreferences,
               navController: NavHostController = rememberNavController()) {
    var analytics: AnalyticsManager = AnalyticsManager(context)
    val authManager: AuthManager = AuthManager(context)
    val firestoreManager = FirestoreManager(context)

    val user: FirebaseUser? = authManager.getCurrentUser()

    // Declare a mutable state to hold the list of posts
    val (posts, setPosts) = remember { mutableStateOf<List<Post>>(emptyList()) }

    // Fetch posts from Firestore when the component is first composed
    LaunchedEffect(Unit) {
        val fetchedPosts = firestoreManager.fetchPostsFromFirestore()
        setPosts(fetchedPosts)
    }

    Screen {
        NavHost(
            navController = navController,
            startDestination = if(user == null) Routes.Login.route else Routes.Home.route
        ) {
            composable(Routes.Login.route) {
                LoginScreen(
                    analytics = analytics,
                    auth = authManager,
                    navigation = navController,
                )
            }
            composable(Routes.Home.route) {
                HomeScreen(
                    analytics = analytics,
                    auth = authManager,
                    navigation = navController,
                    posts = posts,
                    sharedPreferences = sharedPreferences,
                )
            }
            composable(Routes.SignUp.route) {
                SignUpScreen(
                    analytics = analytics,
                    auth = authManager,
                    navigation = navController
                )
            }
            composable(Routes.ForgotPassword.route) {
                ForgotPasswordScreen(
                    analytics = analytics,
                    auth = authManager,
                    navigation = navController
                )
            }
            composable(Routes.ProfileScreen.route) {
                ProfileScreen(
                    authManager = authManager,
                    navigation = navController
                )
            }
        }
    }
}