package ca.centennial.finalproyect.ui

/*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import ca.centennial.finalproyect.ui.screens.home_screen.HomeScreen_2
import ca.centennial.finalproyect.ui.screens.onboarding.gender.GenderSelectionScreen
import ca.centennial.finalproyect.ui.screens.onboarding.goal.GoalScreen
import ca.centennial.finalproyect.ui.screens.onboarding.height.HeightScreen
import ca.centennial.finalproyect.ui.screens.onboarding.nutrient_goal.NutrientGoalScreen
import ca.centennial.finalproyect.ui.screens.onboarding.weight.WeightScreen
import ca.centennial.finalproyect.ui.screens.onboarding.welcome.WelcomeScreen
import ca.centennial.finalproyect.ui.screens.search.SearchScreen
import ca.centennial.finalproyect.ui.theme.NutriMateTheme
import ca.centennial.finalproyect.utils.Routes
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class OnboardActivity : ComponentActivity() {

   */
/* @Inject
    lateinit var sharedPreferences: DefaultSharedPreferences*//*

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val isOnBoardingShown = sharedPreferences.getIsOnBoardingShown()
        setContent {
            NutriMateTheme {
                val navController = rememberNavController()
                val snackbarHostState = remember {
                    SnackbarHostState()
                }
                Scaffold(snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }) { innerPadding ->
                    // Screen content
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = if (isOnBoardingShown) Routes.ROUTE_HOME_SCREEN else Routes.ROUTE_WELCOME
                        ) {
                            composable(Routes.ROUTE_WELCOME) {
                                WelcomeScreen() {
                                    navController.navigate(Routes.ROUTE_GENDER)
                                }
                            }
                            composable(Routes.ROUTE_GENDER) {
                                GenderSelectionScreen(navClickCallback = {
                                    navController.navigate(Routes.ROUTE_WEIGHT)
                                })
                            }
                            composable(Routes.ROUTE_WEIGHT) {
                                WeightScreen(navClickCallback = {
                                    navController.navigate(Routes.ROUTE_HEIGHT)
                                })
                            }
                            composable(Routes.ROUTE_HEIGHT) {
                                HeightScreen(navClickCallback = {
                                    navController.navigate(Routes.ROUTE_GOAL)
                                })
                            }
                            composable(Routes.ROUTE_GOAL) {
                                GoalScreen(navClickCallback = {
                                    navController.navigate(Routes.ROUTE_NUTRITION_ROUTE)
                                })
                            }
                            */
/*composable(Routes.ROUTE_NUTRITION_ROUTE) {
                                sharedPreferences.setOnBoardingShown(true)
                                NutrientGoalScreen(snackbarHostState, onNextClick = {
                                    navController.navigate(
                                        Routes.ROUTE_HOME_SCREEN
                                    ) {
                                        //popup everything upto Route_nutrition and pop that as well
                                        popUpTo(Routes.ROUTE_HOME_SCREEN) {
                                            inclusive = false
                                        }
                                    }
                                })
                            }*//*

                            composable(
                                Routes.ROUTE_SEARCH_FOOD,
                                arguments = listOf(navArgument("mealType") {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val mealType = 1//backStackEntry.arguments?.getInt("mealType")
                                SearchScreen(snackbarHostState, mealType ?: 0)
                            }
                            composable(Routes.ROUTE_HOME_SCREEN) {
                                HomeScreen_2() { mealType ->
                                    navController.navigate("searchFood/${mealType}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
*/
