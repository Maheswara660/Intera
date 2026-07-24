package com.maheswara660.intera.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maheswara660.intera.ui.components.*
import com.maheswara660.intera.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

// ═══════════════════════════════════════════════
// DETAILS SCREEN
// ═══════════════════════════════════════════════

private data class TeamMember(val initials: String, val name: String, val role: String, val color: Color)
private data class Milestone(val title: String, val dueDate: String, val done: Boolean)
private data class TaskEntry(val name: String, val assignee: String, val priority: String, val priorityColor: Color, val done: Boolean)

@Composable
fun DetailsScreen(
    project: ProjectItem = sampleProjects[0],
    onBack: () -> Unit = {},
) {
    val scrollState = rememberScrollState()

    val team = remember {
        listOf(
            TeamMember("MR", "User", "Lead Dev", NeonGreen),
            TeamMember("AK", "Alex Kumar", "UI Designer", ElectricBlue),
            TeamMember("NP", "Nisha Patel", "Data Scientist", NeonGreenDim),
            TeamMember("SR", "Sam Raj", "DevOps", WarningAmber),
            TeamMember("LL", "Lina Liu", "QA Engineer", ElectricBlueDim),
        )
    }

    val milestones = remember {
        listOf(
            Milestone("Project Kickoff", "Jan 10, 2026", true),
            Milestone("Alpha Release", "Feb 28, 2026", true),
            Milestone("Beta Testing", "Apr 15, 2026", true),
            Milestone("Performance Audit", "May 30, 2026", false),
            Milestone("Production Launch", "Jul 20, 2026", false),
        )
    }

    val tasks = remember {
        listOf(
            TaskEntry("Implement OAuth 2.0 flow", "@alex_dev", "High", ErrorRed, true),
            TaskEntry("Design system tokens update", "@nisha", "Medium", WarningAmber, true),
            TaskEntry("API rate limiting", "@sam_raj", "High", ErrorRed, false),
            TaskEntry("Onboarding animation screens", "@lina", "Low", NeonGreenDim, false),
            TaskEntry("Performance profiling", "@maheswara", "High", ErrorRed, false),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkNavy)
            .drawBehind { drawDotGrid() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            // ── TOP BAR ──────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(DarkSurface, DarkNavy))
                    )
                    .padding(top = 24.dp, start = 8.dp, end = 20.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(Modifier.width(4.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = TextPrimary,
                        )
                        Text(
                            text = project.category,
                            fontSize = 11.sp,
                            color = ElectricBlue,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.8.sp,
                        )
                    }
                    StatusBadge(text = project.status, color = project.statusColor)
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── CIRCULAR PROGRESS + STATS ─────────────────
            InteraCard(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    CircularProgressRing(progress = project.progress, modifier = Modifier.size(110.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        MetricRow(label = "Completion", value = "${(project.progress * 100).toInt()}%", NeonGreen)
                        MetricRow(label = "Members", value = "${project.members}", ElectricBlue)
                        MetricRow(label = "Open Tasks", value = "3", WarningAmber)
                        MetricRow(label = "Bugs", value = "1", ErrorRed)
                    }
                }

                Spacer(Modifier.height(16.dp))
                NeonDivider()
                Spacer(Modifier.height(12.dp))

                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                )
            }

            // ── TEAM MEMBERS ─────────────────────────────
            Spacer(Modifier.height(24.dp))
            SectionHeader(
                title = "Team",
                subtitle = "${team.size} members",
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                team.forEach { member ->
                    TeamMemberRow(member)
                }
            }

            // ── MILESTONES ───────────────────────────────
            Spacer(Modifier.height(24.dp))
            SectionHeader(
                title = "Milestones",
                subtitle = "${milestones.count { it.done }} / ${milestones.size} done",
                modifier = Modifier.padding(horizontal = 20.dp),
            )
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                milestones.forEachIndexed { index, milestone ->
                    MilestoneRow(milestone, isLast = index == milestones.lastIndex)
                }
            }

            // ── TASKS ────────────────────────────────────
            Spacer(Modifier.height(24.dp))
            SectionHeader(
                title = "Tasks",
                subtitle = "${tasks.count { it.done }} completed",
                modifier = Modifier.padding(horizontal = 20.dp),
                action = {
                    InteraOutlineButton(text = "Add Task", onClick = {}, icon = Icons.Default.Add)
                },
            )
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                tasks.forEach { task -> TaskRow(task) }
            }

            // ── QUICK ACTIONS ────────────────────────────
            Spacer(Modifier.height(28.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InteraButton(
                    text = "Edit Project",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Edit,
                )
                InteraOutlineButton(
                    text = "Share",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Share,
                )
            }

            Spacer(Modifier.height(100.dp))
        }
    }
}

// ─────────────────────────────────────────────
// Circular progress ring (Canvas)
// ─────────────────────────────────────────────
@Composable
private fun CircularProgressRing(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "CircularProgress",
    )

    Box(
        modifier = modifier.drawBehind {
            val strokeWidth = 12.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2f
            val center = androidx.compose.ui.geometry.Offset(size.width / 2, size.height / 2)

            // Track
            drawCircle(
                color = BorderSubtle,
                radius = radius,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
            // Progress arc — green → blue gradient approximated by solid green (Compose arc)
            drawArc(
                brush = accentGradient,
                startAngle = -90f,
                sweepAngle = 360f * animatedProgress,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            )
        },
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
            )
            Text(text = "Done", color = TextSecondary, fontSize = 11.sp)
        }
    }
}

// ─────────────────────────────────────────────
// Helpers
// ─────────────────────────────────────────────
@Composable
private fun MetricRow(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Text(text = label, color = TextSecondary, fontSize = 12.sp, modifier = Modifier.width(80.dp))
        Text(text = value, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun TeamMemberRow(member: TeamMember) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(CardSurface)
            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(member.color.copy(alpha = 0.15f))
                .border(1.dp, member.color.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(member.initials, color = member.color, fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(member.name, color = TextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            Text(member.role, color = TextSecondary, fontSize = 11.sp)
        }
        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = TextDisabled, modifier = Modifier.size(16.dp))
    }
}

@Composable
private fun MilestoneRow(milestone: Milestone, isLast: Boolean) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        if (milestone.done) NeonGreen.copy(alpha = 0.15f)
                        else BorderSubtle
                    )
                    .border(
                        2.dp,
                        if (milestone.done) NeonGreen else TextDisabled,
                        CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                if (milestone.done) {
                    Icon(Icons.Default.Check, contentDescription = null, tint = NeonGreen, modifier = Modifier.size(12.dp))
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(28.dp)
                        .background(if (milestone.done) NeonGreen.copy(0.3f) else BorderSubtle)
                )
            }
        }
        Column(modifier = Modifier.padding(top = 1.dp)) {
            Text(
                text = milestone.title,
                color = if (milestone.done) TextPrimary else TextSecondary,
                fontSize = 13.sp,
                fontWeight = if (milestone.done) FontWeight.Medium else FontWeight.Normal,
            )
            Text(text = milestone.dueDate, color = TextDisabled, fontSize = 11.sp)
        }
    }
}

@Composable
private fun TaskRow(task: TaskEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(CardSurface)
            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            if (task.done) Icons.Default.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (task.done) SuccessGreen else TextDisabled,
            modifier = Modifier.size(18.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = task.name,
                color = if (task.done) TextSecondary else TextPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
            )
            Text(task.assignee, color = ElectricBlue, fontSize = 11.sp)
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(task.priorityColor.copy(alpha = 0.12f))
                .padding(horizontal = 8.dp, vertical = 3.dp),
        ) {
            Text(text = task.priority, color = task.priorityColor, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}
