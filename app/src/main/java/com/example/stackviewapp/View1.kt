package com.example.stackviewapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import kotlinx.coroutines.launch

@Composable
fun MainView(
    modifier: Modifier = Modifier,
    onButtonClicked1 : (Int) -> Unit
){
    var amount = 0
    val scope = rememberCoroutineScope()

    Column (
        modifier = modifier.background(MaterialTheme.colorScheme.primary)
    ){
        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {},
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {},
                modifier = Modifier
            ) {
                Icon(
                    painter = painterResource(R.drawable.question_mark),
                    contentDescription = "Help",
                )
            }
        }

        Column(
            modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                text = "nikunj how much do you need?",
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "move the dial and set any amount you need upto ₹500",
                modifier = Modifier.padding(start = 16.dp, end = 48.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.secondary)
                    .fillMaxSize()
            ){
                CustomCircularProgressIndicator(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(325.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .align(Alignment.TopCenter),
                    initialValue = 50,
                    minValue = 50,
                    maxValue = 500,
                    primaryColor = Color.Red,
                    secondaryColor = Color.Gray,
                    circleRadius = 230f,
                    onPositionChange = { value ->
                            amount = value
                    }
                )
                Button(
                    onClick = { onButtonClicked1(amount) },
                    shape = RectangleShape,
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .fillMaxWidth()
                        .height(75.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Text(
                        text = "Proceed to EMI Section"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainViewPreview(){
    AppTheme {
        MainView(Modifier.fillMaxSize(), onButtonClicked1 = {})
    }
}