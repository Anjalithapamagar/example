package ca.centennial.finalproyect.ui.screens.storage


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import ca.centennial.finalproyect.ui.screens.OnboardingViewModel
import ca.centennial.finalproyect.ui.screens.onboarding.welcome.WelcomeScreen
import ca.centennial.finalproyect.ui.theme.NutriMateTheme
import ca.centennial.finalproyect.utils.Routes

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.centennial.finalproyect.data.local.DefaultSharedPreferences
import ca.centennial.finalproyect.ui.screens.HomeScreen
import ca.centennial.finalproyect.ui.screens.home_screen.HomeScreen_2
import ca.centennial.finalproyect.ui.screens.onboarding.goal.GoalScreen
import ca.centennial.finalproyect.ui.screens.onboarding.nutrient_goal.NutrientGoalScreen
import ca.centennial.finalproyect.ui.screens.search.SearchScreen


/*val snackbarHostState = remember {
    SnackbarHostState()
}*//*val snackbarHostState = remember {
    SnackbarHostState()
}*/

@Composable
fun OnboardingScreen( context: Context, sharedPreferences: DefaultSharedPreferences , viewModel: OnboardingViewModel = hiltViewModel()) {
    // Convierte LiveData a Flow y luego colecta como State
    val navController = rememberNavController()
    //val defaultSharedPreferences: DefaultSharedPreferences = hiltViewModel()
    OnboardingNavigation(context, navController, sharedPreferences)
}
/*@Composable
fun OnboardingScreen( viewModel: OnboardingViewModel = hiltViewModel()) {
    // Convierte LiveData a Flow y luego colecta como State
    val isOnBoardingShown by viewModel.isOnBoardingShown.asFlow().collectAsState(initial = false)
    val navController = rememberNavController()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    NutriMateTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (1 == 2) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.ROUTE_HOME_SCREEN) {
                            popUpTo(Routes.ROUTE_WELCOME) { inclusive = true }
                        }
                    }
                } else {
                    WelcomeScreen {
                        viewModel.setOnBoardingShown()
                        navController.navigate(Routes.ROUTE_GENDER)
                    }



                    *//*composable(Routes.ROUTE_GENDER) {
                        GenderSelectionScreen(navClickCallback = {
                            navController.navigate(Routes.ROUTE_WEIGHT)
                        })
                    }*//*


                }
            }
        }
    }
}*/


@Composable
fun OnboardingNavigation(context: Context, navController: NavHostController, sharedPreferences: DefaultSharedPreferences) {
    // Definir el viewModel para cada pantalla aquí puede no ser necesario
    // si el viewModel se inyecta dentro de cada pantalla composable automáticamente por Hilt


    val snackbarHostState = remember {
        SnackbarHostState()
    }
    NavHost(navController = navController, startDestination = Routes.ROUTE_WELCOME) {
        composable(Routes.ROUTE_WELCOME) {
            // Asumiendo que WelcomeScreen no necesita navegar automáticamente
            WelcomeScreen(
                navClickCallback = { navController.navigate(Routes.ROUTE_GOAL) }
            )
        }
        composable(Routes.ROUTE_GOAL) {
            GoalScreen(
                navClickCallback = { navController.navigate(Routes.ROUTE_NUTRITION_ROUTE) }
            )
        }
        composable(Routes.ROUTE_NUTRITION_ROUTE) {
            val snackbarHostState = remember { SnackbarHostState() }
            NutrientGoalScreen(
                snackbarHostState = snackbarHostState,
                onNextClick = {
                    // val sharedPreferences: DefaultSharedPreferences = DefaultSharedPreferences(context)
                    sharedPreferences.setOnBoardingShown(true)
                    navController.navigate(Routes.ROUTE_HOME_SCREEN) {
                        popUpTo(Routes.ROUTE_WELCOME) { inclusive = true }
                    }
                }
            )
        }

        composable(
            Routes.ROUTE_SEARCH_FOOD,
            arguments = listOf(navArgument("mealType") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val mealType = backStackEntry.arguments?.getInt("mealType")
            SearchScreen(snackbarHostState, mealType ?: 0)
        }
        composable(Routes.ROUTE_HOME_SCREEN) {
            HomeScreen_2() { mealType ->
                navController.navigate("searchFood/${mealType}")
            }
        }


    }
}

@Composable
fun OnboardingNavGraph(navController: NavHostController) {
    /*NavHost(navController = navController, startDestination = Routes.ROUTE_WELCOME) {
        composable(Routes.ROUTE_WELCOME) {
            WelcomeScreen(navController)
        }
        composable(Routes.ROUTE_GENDER) {
            GenderSelectionScreen(navController)
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

        // Agregar más destinos para el onboarding según sea necesario
    }*/
}
/*
@Composable
fun OnboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel = hiltViewModel()) {
    val isOnBoardingShown by viewModel.isOnBoardingShown.observeAsState(initial = false)

    NutriMateTheme {
        Scaffold { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                if (isOnBoardingShown) {
                    LaunchedEffect(Unit) {
                        navController.navigate(Routes.ROUTE_HOME_SCREEN) {
                            popUpTo(Routes.ROUTE_WELCOME) { inclusive = true }
                        }
                    }
                } else {
                    WelcomeScreen {
                        viewModel.setOnBoardingShown()
                        navController.navigate(Routes.ROUTE_GENDER)
                    }
                }
            }
        }
    }
}*/
