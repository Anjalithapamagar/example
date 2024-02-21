package ca.centennial.finalproyect.ui.screens.db

import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.RealtimeManager

@Composable
fun DailyMealPlanScreen(realtime: RealtimeManager, authManager: AuthManager) {
    var showAddContactDialog by remember { mutableStateOf(false) }

    //val contacts by realtime.getContactsFlow().collectAsState(emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        Text(
            text = "Welcome Anjali",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = Color.White
        )
        LazyColumn {
            items(listOf("Breakfast", "Lunch", "Mid-morning snack", "Afternoon snack", "Dinner")) { meal ->
                MealItem(meal = meal)
            }
            item {
                CalorieSummary()
            }
        }
    }
}

@Composable
fun MealItem(meal: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            //painter = painterResource(id = ca.centennial.finalproyect.R.drawable.ic_firebase),
            painter = painterResource(id = ca.centennial.finalproyect.R.drawable.ic_firebase ),
            contentDescription = "Meal Image",
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = meal, color = Color.White)
    }
}

@Composable
fun CalorieSummary() {
    // Replace with actual calorie summary data
    val totalCalories = 572
    val remainingCalories = 621 - totalCalories
    val proteinConsumed = 57
    val proteinRemaining = 72 - proteinConsumed
    val fatRemaining = 17
    val carbohydratesRemaining = 26

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(text = "Total calories: $totalCalories / 621 cal", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Protein: $proteinConsumed g / $proteinRemaining g left", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Fat: $fatRemaining g left", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Carbohydrates: $carbohydratesRemaining g left", color = Color.Black)
    }
}
