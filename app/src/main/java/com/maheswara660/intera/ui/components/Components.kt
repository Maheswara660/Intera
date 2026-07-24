package com.maheswara660.intera.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maheswara660.intera.ui.theme.*

// ─────────────────────────────────────────────
// Gradient brush helpers
// ─────────────────────────────────────────────
val accentGradient = Brush.linearGradient(
    colors = listOf(NeonGreen, ElectricBlue),
)

val darkSurfaceGradient = Brush.verticalGradient(
    colors = listOf(DarkNavy, DarkSurface),
)

val cardGradient = Brush.linearGradient(
    colors = listOf(CardSurface, ElevatedCard),
)

// ─────────────────────────────────────────────
// Neon glow line divider
// ─────────────────────────────────────────────
@Composable
fun NeonDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                Brush.horizontalGradient(
                    listOf(Color.Transparent, NeonGreen.copy(alpha = 0.6f), ElectricBlue.copy(alpha = 0.6f), Color.Transparent)
                )
            )
    )
}

// ─────────────────────────────────────────────
// Accent gradient card
// ─────────────────────────────────────────────
@Composable
fun InteraCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(cardGradient)
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(NeonGreen.copy(alpha = 0.3f), ElectricBlue.copy(alpha = 0.3f))
                ),
                shape = RoundedCornerShape(16.dp),
            )
            .then(
                if (onClick != null)
                    Modifier.clickable(interactionSource = interactionSource, indication = null) { onClick() }
                else Modifier
            )
    ) {
        Column(modifier = Modifier.padding(16.dp), content = content)
    }
}

// ─────────────────────────────────────────────
// Gradient accent button
// ─────────────────────────────────────────────
@Composable
fun InteraButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (enabled) accentGradient
                else Brush.linearGradient(listOf(TextDisabled, TextDisabled))
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 24.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DarkNavy,
                    modifier = Modifier.size(18.dp),
                )
            }
            Text(
                text = text,
                color = DarkNavy,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

// ─────────────────────────────────────────────
// Ghost / outline button
// ─────────────────────────────────────────────
@Composable
fun InteraOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                width = 1.dp,
                brush = accentGradient,
                shape = RoundedCornerShape(12.dp),
            )
            .clickable { onClick() }
            .padding(horizontal = 24.dp, vertical = 13.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = NeonGreen,
                    modifier = Modifier.size(18.dp),
                )
            }
            Text(
                text = text,
                color = NeonGreen,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

// ─────────────────────────────────────────────
// Section header with accent line
// ─────────────────────────────────────────────
@Composable
fun SectionHeader(
    title: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    action: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(18.dp)
                        .background(accentGradient, RoundedCornerShape(2.dp))
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextPrimary,
                )
            }
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    modifier = Modifier.padding(start = 11.dp),
                )
            }
        }
        action?.invoke()
    }
}

// ─────────────────────────────────────────────
// Stat / metric chip
// ─────────────────────────────────────────────
@Composable
fun StatChip(
    label: String,
    value: String,
    color: Color = NeonGreen,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(color.copy(alpha = 0.08f))
            .border(1.dp, color.copy(alpha = 0.25f), RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = value, color = color, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(Modifier.height(2.dp))
        Text(text = label, color = TextSecondary, fontSize = 11.sp, letterSpacing = 0.4.sp)
    }
}

// ─────────────────────────────────────────────
// Animated progress bar
// ─────────────────────────────────────────────
@Composable
fun NeonProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    trackColor: Color = BorderSubtle,
    barBrush: Brush = accentGradient,
    height: Dp = 6.dp,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "NeonProgress",
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(50))
            .background(trackColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(animatedProgress)
                .fillMaxHeight()
                .background(barBrush, RoundedCornerShape(50)),
        )
    }
}

// ─────────────────────────────────────────────
// Status badge
// ─────────────────────────────────────────────
@Composable
fun StatusBadge(
    text: String,
    color: Color = SuccessGreen,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .border(1.dp, color.copy(alpha = 0.35f), RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .background(color, CircleShape)
        )
        Text(text = text, color = color, fontSize = 11.sp, fontWeight = FontWeight.Medium)
    }
}

// ─────────────────────────────────────────────
// Dot-grid decorative background (drawn with Canvas)
// ─────────────────────────────────────────────
fun DrawScope.drawDotGrid(
    dotColor: Color = NeonGreen.copy(alpha = 0.06f),
    spacing: Float = 30f,
    dotRadius: Float = 1.5f,
) {
    var x = 0f
    while (x < size.width) {
        var y = 0f
        while (y < size.height) {
            drawCircle(color = dotColor, radius = dotRadius, center = Offset(x, y))
            y += spacing
        }
        x += spacing
    }
}
