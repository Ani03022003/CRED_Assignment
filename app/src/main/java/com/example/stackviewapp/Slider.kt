package com.example.stackviewapp

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.*


@Composable
fun CustomCircularProgressIndicator(
    modifier: Modifier = Modifier,
    initialValue:Int,
    primaryColor: Color,
    secondaryColor:Color,
    minValue:Int = 0,
    maxValue:Int = 100,
    circleRadius:Float,
    onPositionChange:(Int)->Unit
) {
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }

    var changeAngle by remember {
        mutableStateOf(0f)
    }

    var dragStartedAngle by remember {
        mutableStateOf(0f)
    }

    var oldPositionValue by remember {
        mutableStateOf(initialValue)
    }

    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(true){
                    detectDragGestures(
                        onDragStart = {offset ->
                            dragStartedAngle = -atan2(
                                x = circleCenter.y - offset.y,
                                y = circleCenter.x - offset.x
                            ) * (180f / PI).toFloat()
                            dragStartedAngle = (dragStartedAngle + 180f).mod(360f)
                        },
                        onDrag = { change, _ ->
                            scope.launch(Dispatchers.Default) {
                                var touchAngle = -atan2(
                                    x = circleCenter.y - change.position.y,
                                    y = circleCenter.x - change.position.x
                                ) * (180f / PI).toFloat()
                                touchAngle = (touchAngle + 180f).mod(360f)

                                val currentAngle = oldPositionValue * 360f / (maxValue - minValue)
                                changeAngle = touchAngle - currentAngle

                                val lowerThreshold =
                                    currentAngle - (360f / (maxValue - minValue) * 5)
                                val higherThreshold =
                                    currentAngle + (360f / (maxValue - minValue) * 5)

                                if (dragStartedAngle in lowerThreshold..higherThreshold) {
                                    positionValue =
                                        (oldPositionValue + (changeAngle / (360f / (maxValue - minValue))).roundToInt())
                                }
                            }
                        },
                        onDragEnd = {
                            oldPositionValue = positionValue
                            onPositionChange(positionValue)
                        }
                    )
                }
        ){
            val width = size.width
            val height = size.height
            val circleThickness = width / 25f
            circleCenter = Offset(x = width/2f, y = height/2f)

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )


            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )

            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )

            )

            val outerRadius = circleRadius + circleThickness/2f
            val gap = 15f
            for (i in 0 .. (maxValue-minValue)){
                val color = if(i < positionValue-minValue) primaryColor else primaryColor.copy(alpha = 0.3f)
                val angleInDegrees = i*360f/(maxValue-minValue).toFloat()
                val angleInRad = angleInDegrees * PI / 180f + PI/2f

                val yGapAdjustment = cos(angleInDegrees * PI / 180f)*gap
                val xGapAdjustment = -sin(angleInDegrees * PI / 180f)*gap

                val start = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleCenter.y + yGapAdjustment).toFloat()
                )

                val end = Offset(
                    x = (outerRadius * cos(angleInRad) + circleCenter.x + xGapAdjustment).toFloat(),
                    y = (outerRadius * sin(angleInRad) + circleThickness + circleCenter.y + yGapAdjustment).toFloat()
                )

                rotate(
                    angleInDegrees,
                    pivot = start
                ){
                    drawLine(
                        color = color,
                        start = start,
                        end = end,
                        strokeWidth = 1.dp.toPx()
                    )
                   }
                }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "credit amount",
                        circleCenter.x + 72.dp.toPx()/3f,
                        circleCenter.y - 60.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 8.sp.toPx()
                            textAlign = Paint.Align.RIGHT
                            color = Color.Black.toArgb()
                            isFakeBoldText = true
                        }
                    )
                    drawText(
                        "₹$positionValue",
                        circleCenter.x - 9.dp.toPx()/3f,
                        circleCenter.y + 45.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 24.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = Color.Black.toArgb()
                            isFakeBoldText = true
                        }
                    )
                    drawText(
                        "@1.04% monthly",
                        circleCenter.x + 81.dp.toPx()/3f,
                        circleCenter.y + 81.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 8.sp.toPx()
                            textAlign = Paint.Align.RIGHT
                            color = Color.Black.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }
        
        Text(
            text = "stash is instant. money will be credited within seconds",
            fontSize = 12.sp,
            minLines = 2,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter).padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CustomCircularProgressIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(Color.DarkGray)
        ,
        initialValue = 50,
        primaryColor = Color.Yellow,
        secondaryColor = Color.Gray,
        circleRadius = 230f,
        onPositionChange = {},
    )
}