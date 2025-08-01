package com.local_expenses.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.local_expenses.R


val MontserratFontFamily = FontFamily(
    Font(R.font.manrope_regular),
    Font(R.font.manrope_medium),
    Font(R.font.marope_bold),
    Font(R.font.manrope_extralight),
    Font(R.font.manrope_extremebold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MontserratFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )
)