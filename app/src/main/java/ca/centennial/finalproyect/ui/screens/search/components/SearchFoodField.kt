package ca.centennial.finalproyect.ui.screens.search.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchFoodField(
    modifier: Modifier = Modifier,
    hint: String,
    shouldShowHint: Boolean,
    value: String,
    onValueChange: (value: String) -> Unit,
    onSearchFood: () -> Unit
) {
    Box(
        modifier = modifier
    )
    {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontSize = 16.sp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .padding(2.dp)
                .shadow(
                    elevation = 0.1.dp,
                    shape = RoundedCornerShape(5.dp),
                    ambientColor = Color.LightGray
                )
                .fillMaxWidth()
                .padding(20.dp)
        )
        if (shouldShowHint)
            Text(
                text = hint,
                fontWeight = FontWeight.Light,
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp)
            )
        IconButton(onClick = { onSearchFood() }, modifier = Modifier.align(Alignment.CenterEnd)) {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchFoodFieldPreview() {
    SearchFoodField(Modifier, "Search..", true, "", {}, {})
}