package com.example.hellocompose.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = CutCornerShape(
        topStart = 20.dp,
        topEnd = 0.dp,
        bottomEnd = 20.dp,
        bottomStart = 0.dp
    ),
    large = RoundedCornerShape(0.dp)
)