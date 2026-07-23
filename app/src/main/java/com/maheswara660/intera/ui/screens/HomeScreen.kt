package com.maheswara660.intera.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maheswara660.intera.ui.components.*
import com.maheswara660.intera.ui.theme.*

// ═══════════════════════════════════════════════
// HOME SCREEN
// ═══════════════════════════════════════════════

@Composable
fun HomeScreen(
    onProjectClick: (ProjectItem) -> Unit = {},
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }
    val filters = listOf("All", "AI/ML", "Analytics", "Backend", "AR/VR")

    val filteredProjects = remember(searchQuery, selectedFilter) {
        sampleProjects.filter { project ->
            val matchesSearch = project.title.contains(searchQuery, ignoreCase = true) ||
                    project.description.contains(searchQuery, ignoreCase = true)
            val matchesFilter = selectedFilter == "All" || project.category == selectedFilter
            matchesSearch && matchesFilter
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
            .drawBehind { drawDotGrid() }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp),
        ) {
            // ── TOP BANNER / HEADER ──────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(listOf(DarkSurface, DarkNavy))
                        )
                        .padding(top = 24.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = "Good Evening 👾",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary,
                                )
                                Spacer(Modifier.height(2.dp))
                                Text(
                                    text = "User",
                                    style = MaterialTheme.typography.displayMedium,
                                    color = TextPrimary,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(accentGradient),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("M", color = DarkNavy, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                        }

                        Spacer(Modifier.height(20.dp))
                        NeonDivider()
                        Spacer(Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            StatChip("Projects", "4", NeonGreen, Modifier.weight(1f))
                            StatChip("Active", "2", ElectricBlue, Modifier.weight(1f))
                            StatChip("Members", "14", NeonGreenDim, Modifier.weight(1f))
                        }
                    }
                }
            }

            // ── SEARCH BAR ───────────────────────────────
            item {
                Spacer(Modifier.height(20.dp))
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    placeholder = {
                        Text("Search projects…", color = TextDisabled, fontSize = 14.sp)
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search", tint = TextSecondary)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Clear, contentDescription = "Clear", tint = TextSecondary)
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonGreen,
                        unfocusedBorderColor = BorderSubtle,
                        cursorColor = NeonGreen,
                        focusedLabelColor = NeonGreen,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        unfocusedContainerColor = CardSurface,
                        focusedContainerColor = CardSurface,
                    ),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true,
                )
            }

            // ── FILTER CHIPS ─────────────────────────────
            item {
                Spacer(Modifier.height(16.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(filters) { filter ->
                        val isSelected = filter == selectedFilter
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .background(
                                    if (isSelected) accentGradient
                                    else Brush.linearGradient(listOf(CardSurface, CardSurface))
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) Color.Transparent else BorderSubtle,
                                    RoundedCornerShape(50),
                                )
                                .clickable { selectedFilter = filter }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        ) {
                            Text(
                                text = filter,
                                color = if (isSelected) DarkNavy else TextSecondary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp,
                            )
                        }
                    }
                }
            }

            // ── PROJECTS SECTION ─────────────────────────
            item {
                Spacer(Modifier.height(24.dp))
                SectionHeader(
                    title = "Projects",
                    subtitle = "${filteredProjects.size} of ${sampleProjects.size} projects",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    action = {
                        IconButton(onClick = {}) {
                            Icon(Icons.Default.Add, contentDescription = "Add", tint = NeonGreen)
                        }
                    },
                )
            }

            items(filteredProjects, key = { it.id }) { project ->
                Spacer(Modifier.height(12.dp))
                ProjectCard(
                    project = project,
                    onClick = { onProjectClick(project) },
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
            }

            // ── RECENT ACTIVITY ──────────────────────────
            item {
                Spacer(Modifier.height(28.dp))
                SectionHeader(
                    title = "Recent Activity",
                    modifier = Modifier.padding(horizontal = 20.dp),
                )
                Spacer(Modifier.height(12.dp))
            }

            items(sampleActivity, key = { it.title + it.time }) { activity ->
                ActivityRow(activity = activity)
            }
        }
    }
}

// ─────────────────────────────────────────────
// Project Card
// ─────────────────────────────────────────────
@Composable
fun ProjectCard(
    project: ProjectItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    InteraCard(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = TextPrimary,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = project.category,
                    fontSize = 10.sp,
                    color = ElectricBlue,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.8.sp,
                )
            }
            StatusBadge(text = project.status, color = project.statusColor)
        }

        Spacer(Modifier.height(10.dp))
        Text(
            text = project.description,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${(project.progress * 100).toInt()}% complete",
                fontSize = 11.sp,
                color = NeonGreen,
                fontWeight = FontWeight.Medium,
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(Icons.Default.Person, contentDescription = null, tint = TextDisabled, modifier = Modifier.size(12.dp))
                Text(text = "${project.members}", fontSize = 11.sp, color = TextDisabled)
            }
        }
        Spacer(Modifier.height(6.dp))
        NeonProgressBar(progress = project.progress)
    }
}

// ─────────────────────────────────────────────
// Activity Row
// ─────────────────────────────────────────────
@Composable
fun ActivityRow(activity: ActivityItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(activity.color.copy(alpha = 0.12f))
                .border(1.dp, activity.color.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(activity.icon, contentDescription = null, tint = activity.color, modifier = Modifier.size(18.dp))
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(text = activity.title, style = MaterialTheme.typography.titleSmall, color = TextPrimary, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = activity.subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary, maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
        Text(text = activity.time, style = MaterialTheme.typography.bodySmall, color = TextDisabled)
    }
}
