package com.example.stackviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.AppTheme
import java.util.Collections


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Sheet(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Sheet(modifier: Modifier = Modifier) {
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }
    var isSheetOpen2 by rememberSaveable {
        mutableStateOf(false)
    }
    var loanAmount by rememberSaveable {
        mutableIntStateOf(500)
    }

    val myList = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { mutableStateListOf<String>().apply { addAll(it) } } // Cast 'it' to List<String> here
        )
    ) {
        mutableStateListOf<String>()
    }

    MainView(modifier,
        onButtonClicked1 = { amt ->
            isSheetOpen = true
            loanAmount = amt
        })

    if(isSheetOpen){
        SlideView1(modifier = Modifier,
            amount = loanAmount,
            onClose = {
                isSheetOpen = false
            },
            onOpen = { data ->
                myList.add(0,data.emi)
                myList.add(1,data.duration)
                isSheetOpen2 = true
            })
    }

    if(isSheetOpen2){
        SlideView2(
            modifier = Modifier,
            emiData = myList,
            onClose = { isSheetOpen2 = false}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppTheme {
        Sheet(Modifier.fillMaxSize())
    }
}