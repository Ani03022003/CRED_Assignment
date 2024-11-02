package com.example.stackviewapp

import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BankData(
    val title : String,
    val subtitle : String,
    @DrawableRes val logo : Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlideView2(
    modifier: Modifier = Modifier,
    onClose : () -> Unit,
    emiData : MutableList<String>
){

    val bankData = listOf(
        BankData("HDFC BANK", "897458935", R.drawable.hdfc),
        BankData("SBI", "897458935", R.drawable.sbi),
        BankData("PNB", "8974589334535", R.drawable.pnb)
    )

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.height(575.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "EMI")
                    Text(text = emiData[0])
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Duration")
                    Text(text = emiData[1])
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Text(
                    text = "where should we send the money?",
                    modifier = Modifier.padding(start = 16.dp))
                Text(
                    text = "amount will be credited to the bank account. EMI will also be debited from this bank account",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(32.dp))
                for(data in bankData){
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        modifier = modifier.fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(255.dp)
                        ) {
                            Image(
                                painter = painterResource(data.logo),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(40.dp)
                            )
                            Column (
                                modifier = Modifier.weight(1f).padding(start = 16.dp)
                            ){
                                Text(
                                    text = data.title,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                                Text(
                                    text = data.subtitle,
                                    style = MaterialTheme.typography.titleSmall,
                                )
                            }
                            Checkbox(
                                checked = false,
                                onCheckedChange = {}
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                }
                Button(
                    onClick = {},
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "change account")
                }
                Spacer(modifier = Modifier.padding(24.dp))
                Button(
                    onClick = {},
                    shape = RectangleShape,
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .fillMaxWidth()
                        .height(75.dp)
                ) {
                    Text(text = "Tap for 1-click KYC")
                }
            }
        }
    }
}