package com.example.stackviewapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EmiData(
    val emi : String = "a",
    val duration : String = "b",
    val title : String = "c",
    val subtitle : String = "d",
    var click : MutableState<Boolean> = mutableStateOf(false)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SlideView1(
    modifier: Modifier = Modifier,
    amount: Int = 0,
    onClose : () -> Unit,
    onOpen : (EmiData) -> Unit){

    val data = listOf(
        EmiData(
                emi =  "₹42 /mo",
                duration = "12 months",
                title = "₹42 /mo for 12 months",
                subtitle = "See calculations"),
        EmiData(emi =  "₹55 /mo",
                duration =  "9 months",
                title =  "₹55 /mo for 9 months",
                subtitle =  "See calculations"),
        EmiData(emi = "₹82 /mo",
                duration =  "6 months",
                title =  "₹82 /mo for 6 months",
                subtitle =  "See calculations")
    )

    var selectedItem = EmiData()

    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onClose,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().heightIn(max = 416.dp)
        ) {
            Text("Credit amount", modifier = Modifier.padding(horizontal = 16.dp))
            Text("₹${amount}", modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.padding(8.dp).fillMaxWidth())
            Column(
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Text(
                    text = "how do you wish to repay?",
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp).fillMaxWidth())
                Text(
                    text = "choose one of our recommended plans or make your own",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp).fillMaxWidth())
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    modifier = Modifier
                ) {
                    items(data) { emiData ->
                        Column(
                            modifier = Modifier.clip(RoundedCornerShape(25.dp))
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .size(width = 150.dp, height = 200.dp)
                                .padding(8.dp)
                        ) {
                            IconButton(
                                onClick = {
                                    emiData.click.value = !emiData.click.value
                                    selectedItem = if(emiData.click.value) emiData else EmiData()
                                }
                            ) {
                                if(emiData.click.value) {
                                    Icon(
                                        imageVector = Icons.Outlined.CheckCircle,
                                        contentDescription = null
                                    )
                                }
                                else {
                                    Icon(
                                        painter = painterResource(R.drawable.baseline_radio_button_unchecked_24),
                                        contentDescription = null
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.padding(8.dp).fillMaxWidth())
                            Text(text = emiData.emi)
                            Text(text = emiData.duration)
                            Spacer(modifier = Modifier.padding(8.dp).fillMaxWidth())
                            Text(text = emiData.subtitle)
                        }
                    }
                }
                Button(
                    onClick = {},
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "create your own plan")
                }
                Spacer(modifier = Modifier.padding(32.dp).fillMaxWidth())
                Button(
                    onClick = { if(selectedItem.emi == "a") Toast.makeText(context, "Please select a plan or create a plan", Toast.LENGTH_SHORT).show()
                              else onOpen(selectedItem) },
                    shape = RectangleShape,
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                        .fillMaxWidth()
                        .height(75.dp)
                    ) {
                    Text(text = "Select your bank account")
                }
            }
        }
    }
}