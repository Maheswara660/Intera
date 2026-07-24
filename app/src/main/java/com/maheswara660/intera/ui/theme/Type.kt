package com.maheswara660.intera.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using system default sans-serif which maps to Roboto on Android
val InteraFontFamily = FontFamily.Default

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 32.sp,
        lineHeight = 40.sp,
        color      = TextPrimary,
    ),
    displayMedium = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 26.sp,
        lineHeight = 34.sp,
        color      = TextPrimary,
    ),
    headlineLarge = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize   = 22.sp,
        lineHeight = 30.sp,
        color      = TextPrimary,
    ),
    headlineMedium = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 18.sp,
        lineHeight = 26.sp,
        color      = TextPrimary,
    ),
    headlineSmall = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 15.sp,
        lineHeight = 22.sp,
        color      = TextPrimary,
    ),
    titleLarge = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize   = 17.sp,
        lineHeight = 24.sp,
        color      = TextPrimary,
    ),
    titleMedium = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        color      = TextPrimary,
    ),
    titleSmall = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 12.sp,
        lineHeight = 18.sp,
        color      = TextSecondary,
    ),
    bodyLarge = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 16.sp,
        lineHeight = 24.sp,
        color      = TextPrimary,
    ),
    bodyMedium = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 14.sp,
        lineHeight = 20.sp,
        color      = TextSecondary,
    ),
    bodySmall = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 12.sp,
        lineHeight = 16.sp,
        color      = TextDisabled,
    ),
    labelLarge = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 13.sp,
        letterSpacing = 0.5.sp,
        color      = TextPrimary,
    ),
    labelMedium = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize   = 11.sp,
        letterSpacing = 0.5.sp,
        color      = TextSecondary,
    ),
    labelSmall = TextStyle(
        fontFamily = InteraFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize   = 10.sp,
        letterSpacing = 0.5.sp,
        color      = TextDisabled,
    ),
)