package com.maheswara660.intera.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maheswara660.intera.ui.components.*
import com.maheswara660.intera.ui.theme.*

// ═══════════════════════════════════════════════
// SETTINGS SCREEN
// ═══════════════════════════════════════════════

private data class SettingToggle(
    val key: String,
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val iconColor: Color,
    val default: Boolean = false,
)

private data class SettingAction(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val iconColor: Color,
    val badge: String? = null,
    val destructive: Boolean = false,
)

@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()

    // Toggle states
    val toggleSettings = remember {
        listOf(
            SettingToggle("notifications", Icons.Default.Notifications, "Push Notifications", "Receive alerts for project updates", ElectricBlue, true),
            SettingToggle("dark_mode", Icons.Default.DarkMode, "Force Dark Mode", "Always use dark tech theme", NeonGreen, true),
            SettingToggle("analytics", Icons.Default.Analytics, "Usage Analytics", "Help improve Intera with usage data", TextSecondary, false),
            SettingToggle("biometric", Icons.Default.Fingerprint, "Biometric Lock", "Secure app with fingerprint or face ID", NeonGreen, false),
            SettingToggle("sync", Icons.Default.Sync, "Auto Sync", "Sync projects in background automatically", ElectricBlue, true),
            SettingToggle("sounds", Icons.AutoMirrored.Filled.VolumeUp, "Sound Effects", "Play audio cues for actions", TextSecondary, false),
        )
    }
    val toggleStates = remember { mutableStateMapOf<String, Boolean>().apply { toggleSettings.forEach { put(it.key, it.default) } } }

    var selectedTheme by remember { mutableStateOf("System") }
    var selectedLanguage by remember { mutableStateOf("English") }
    val themes = listOf("System", "Dark", "AMOLED")
    val languages = listOf("English", "Hindi", "Tamil", "Telugu")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
            .drawBehind { drawDotGrid() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            // ── HEADER ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(DarkSurface, DarkNavy)))
                    .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 24.dp),
            ) {
                Column {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.displayMedium,
                        color = TextPrimary,
                    )
                    Text(
                        text = "Customize your Intera experience",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                    )
                    Spacer(Modifier.height(16.dp))
                    NeonDivider()
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── PROFILE CARD ─────────────────────────────
            InteraCard(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Avatar with gradient ring
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .background(accentGradient)
                        )
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(CardSurface),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text("M", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text("User", style = MaterialTheme.typography.titleLarge, color = TextPrimary)
                        Text("user@intera.dev", style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                        Spacer(Modifier.height(6.dp))
                        StatusBadge(text = "Pro Plan", color = NeonGreen)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit profile", tint = NeonGreen)
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── APPEARANCE ───────────────────────────────
            SettingsGroupHeader(title = "Appearance")
            Spacer(Modifier.height(10.dp))

            // Theme Selector
            SegmentedSettingRow(
                icon = Icons.Default.Palette,
                title = "Theme",
                subtitle = "Choose color scheme",
                iconColor = ElectricBlue,
                options = themes,
                selectedOption = selectedTheme,
                onOptionSelected = { selectedTheme = it },
            )
            Spacer(Modifier.height(8.dp))
            // Language Selector
            DropdownSettingRow(
                icon = Icons.Default.Language,
                title = "Language",
                subtitle = "App display language",
                iconColor = NeonGreenDim,
                options = languages,
                selectedOption = selectedLanguage,
                onOptionSelected = { selectedLanguage = it },
            )

            Spacer(Modifier.height(24.dp))

            // ── NOTIFICATIONS & PRIVACY ──────────────────
            SettingsGroupHeader(title = "Notifications & Privacy")
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                toggleSettings.forEachIndexed { index, setting ->
                    ToggleSettingRow(
                        setting = setting,
                        isChecked = toggleStates[setting.key] ?: false,
                        onCheckedChange = { toggleStates[setting.key] = it },
                        isFirst = index == 0,
                        isLast = index == toggleSettings.lastIndex,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── ACCOUNT ACTIONS ──────────────────────────
            SettingsGroupHeader(title = "Account")
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ActionSettingRow(SettingAction(Icons.Default.Lock, "Privacy Policy", "Review data handling practices", ElectricBlue))
                ActionSettingRow(SettingAction(Icons.AutoMirrored.Filled.HelpOutline, "Help & Support", "FAQs, contact support", TextSecondary))
                ActionSettingRow(SettingAction(Icons.Default.NewReleases, "What's New", "Intera v1.0.0 – July 2026", NeonGreen, badge = "NEW"))
                ActionSettingRow(SettingAction(Icons.Default.DeleteForever, "Clear Cache", "Free up local storage space", WarningAmber))
            }

            Spacer(Modifier.height(20.dp))

            // Danger zone
            InteraCard(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    InteraOutlineButton(
                        text = "Log Out",
                        onClick = {},
                        modifier = Modifier.weight(1f),
                        icon = Icons.AutoMirrored.Filled.Logout,
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(ErrorRed.copy(alpha = 0.12f))
                            .border(1.dp, ErrorRed.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .clickable {}
                            .padding(horizontal = 16.dp, vertical = 13.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(16.dp))
                            Text("Delete Account", color = ErrorRed, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }

            // Version footer
            Spacer(Modifier.height(24.dp))
            Text(
                text = "Intera v1.0.0\nBuilt with ❤️ by Maheswara660",
                color = TextDisabled,
                fontSize = 11.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
            Spacer(Modifier.height(100.dp))
        }
    }
}

// ─────────────────────────────────────────────
// Settings section header
// ─────────────────────────────────────────────
@Composable
private fun SettingsGroupHeader(title: String) {
    Text(
        text = title.uppercase(),
        color = NeonGreen,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.2.sp,
        modifier = Modifier.padding(horizontal = 20.dp),
    )
}

// ─────────────────────────────────────────────
// Toggle setting row
// ─────────────────────────────────────────────
@Composable
private fun ToggleSettingRow(
    setting: SettingToggle,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    isFirst: Boolean,
    isLast: Boolean,
) {
    val shape = when {
        isFirst && isLast -> RoundedCornerShape(12.dp)
        isFirst -> RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 2.dp, bottomEnd = 2.dp)
        isLast -> RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp, bottomStart = 12.dp, bottomEnd = 12.dp)
        else -> RoundedCornerShape(2.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .background(CardSurface)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(setting.iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(setting.icon, contentDescription = null, tint = setting.iconColor, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(setting.title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(setting.subtitle, color = TextSecondary, fontSize = 11.sp)
        }
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = DarkNavy,
                checkedTrackColor = NeonGreen,
                uncheckedThumbColor = TextDisabled,
                uncheckedTrackColor = BorderSubtle,
                uncheckedBorderColor = BorderSubtle,
            ),
        )
    }
}

// ─────────────────────────────────────────────
// Segmented control setting
// ─────────────────────────────────────────────
@Composable
private fun SegmentedSettingRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    InteraCard(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Box(
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = TextSecondary, fontSize = 11.sp)
            }
        }
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(ElevatedCard),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) accentGradient
                            else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent))
                        )
                        .clickable { onOptionSelected(option) }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = option,
                        color = if (isSelected) DarkNavy else TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }
    }
}

// ─────────────────────────────────────────────
// Dropdown setting row
// ─────────────────────────────────────────────
@Composable
private fun DropdownSettingRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    iconColor: Color,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(CardSurface)
                .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                .clickable { expanded = true }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(iconColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Text(subtitle, color = TextSecondary, fontSize = 11.sp)
            }
            Text(selectedOption, color = NeonGreen, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Icon(Icons.Default.ExpandMore, contentDescription = null, tint = TextDisabled, modifier = Modifier.size(16.dp))
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(ElevatedCard),
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            color = if (option == selectedOption) NeonGreen else TextPrimary,
                            fontWeight = if (option == selectedOption) FontWeight.Bold else FontWeight.Normal,
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                    leadingIcon = {
                        if (option == selectedOption) {
                            Icon(Icons.Default.Check, contentDescription = null, tint = NeonGreen, modifier = Modifier.size(16.dp))
                        }
                    },
                )
            }
        }
    }
}

// ─────────────────────────────────────────────
// Action setting row
// ─────────────────────────────────────────────
@Composable
private fun ActionSettingRow(setting: SettingAction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardSurface)
            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            .clickable {}
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(setting.iconColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(setting.icon, contentDescription = null, tint = setting.iconColor, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(setting.title, color = if (setting.destructive) ErrorRed else TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(setting.subtitle, color = TextSecondary, fontSize = 11.sp)
        }
        if (setting.badge != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(NeonGreen.copy(alpha = 0.12f))
                    .padding(horizontal = 8.dp, vertical = 2.dp),
            ) {
                Text(setting.badge, color = NeonGreen, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
            }
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextDisabled, modifier = Modifier.size(16.dp))
    }
}
