package com.maheswara660.intera.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val InteraDarkColorScheme = darkColorScheme(
    primary          = NeonGreen,
    onPrimary        = DarkNavy,
    primaryContainer = NeonGreenMuted,
    onPrimaryContainer = NeonGreen,

    secondary          = ElectricBlue,
    onSecondary        = DarkNavy,
    secondaryContainer = ElectricBlueMuted,
    onSecondaryContainer = ElectricBlue,

    tertiary          = NeonGreenDim,
    onTertiary        = DarkNavy,

    background        = DarkNavy,
    onBackground      = TextPrimary,

    surface           = DarkSurface,
    onSurface         = TextPrimary,
    surfaceVariant    = CardSurface,
    onSurfaceVariant  = TextSecondary,

    outline           = BorderSubtle,
    outlineVariant    = BorderAccent,

    error             = ErrorRed,
    onError           = DarkNavy,
)

@Composable
fun InteraTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = InteraDarkColorScheme,
        typography  = Typography,
        content     = content,
    )
}