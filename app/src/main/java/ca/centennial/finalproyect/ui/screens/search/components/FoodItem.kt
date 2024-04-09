package ca.centennial.finalproyect.ui.screens.search.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ca.centennial.finalproyect.domain.TrackableFood

@Composable
fun FoodItem(item: TrackableFood, onItemClick: () -> Unit, modifier: Modifier = Modifier) {

    var foodQuantity by remember {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(10.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(5.dp))
            .border(width = 0.25.dp, shape = RoundedCornerShape(5.dp), color = Color.LightGray)
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(
                item.imageUrl
            ).build(),
            contentDescription = item.productName,
            modifier = Modifier
                .size(75.dp)
                .fillMaxHeight(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1.0f)
                .padding(start = 20.dp, end = 10.dp),
        ) {
            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                Text(
                    text = item.productName, modifier = Modifier
                        .fillMaxWidth(0.5f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${item.caloriesPer100g} g",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${item.proteinPer100g} g",
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "${item.fatPer100g} g",
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row() {
                Text(
                    text = "${item.caloriesPer100g}kcal/100g", modifier = Modifier
                        .fillMaxWidth(0.5f)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Carbs",
                        modifier = Modifier.defaultMinSize(minWidth = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Protein",
                        modifier = Modifier.defaultMinSize(minWidth = 40.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Fat",
                        modifier = Modifier.defaultMinSize(minWidth = 40.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}
