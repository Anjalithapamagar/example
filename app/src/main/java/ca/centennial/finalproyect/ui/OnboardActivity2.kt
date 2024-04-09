package ca.centennial.finalproyect.ui

/*
@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class OnboardActivity_2 : ComponentActivity() {

    @Inject
    lateinit var sharedPreferences: DefaultSharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isOnBoardingShown = sharedPreferences.getIsOnBoardingShown()
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
                            composable(Routes.ROUTE_NUTRITION_ROUTE) {
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
                                HomeScreen() { mealType ->
                                    navController.navigate("searchFood/${mealType}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}*/
