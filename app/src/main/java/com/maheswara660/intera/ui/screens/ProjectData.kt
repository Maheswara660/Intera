package com.maheswara660.intera.ui.screens

import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import com.maheswara660.intera.ui.theme.*

// ═══════════════════════════════════════════════
// Shared data models (internal — visible across the module)
// ═══════════════════════════════════════════════

data class ProjectItem(
    val id: Int,
    val title: String,
    val description: String,
    val progress: Float,
    val status: String,
    val statusColor: Color,
    val category: String,
    val members: Int,
)

data class ActivityItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val subtitle: String,
    val time: String,
    val color: Color,
)

// Exposed as internal so Navigation, DetailsScreen etc. can access it
val sampleProjects = listOf(
    ProjectItem(1, "Neural Interface", "AI-driven UX system with real-time feedback loops.", 0.82f, "Active", SuccessGreen, "AI/ML", 5),
    ProjectItem(2, "Quantum Dashboard", "Data visualization for quantum computing metrics.", 0.60f, "In Progress", ElectricBlue, "Analytics", 3),
    ProjectItem(3, "CyberNet API", "Secure REST API gateway with edge caching layer.", 0.45f, "In Progress", WarningAmber, "Backend", 4),
    ProjectItem(4, "HoloSync", "Holographic sync protocol for AR devices.", 0.20f, "Planning", TextSecondary, "AR/VR", 2),
)

val sampleActivity = listOf(
    ActivityItem(Icons.Default.CheckCircle, "Sprint Review Completed", "Neural Interface · Sprint 7", "2m ago", SuccessGreen),
    ActivityItem(Icons.Default.BugReport, "Bug #247 Resolved", "CyberNet API · Auth module", "18m ago", NeonGreen),
    ActivityItem(Icons.Default.PersonAdd, "New Member Joined", "Quantum Dashboard · @alex_dev", "1h ago", ElectricBlue),
    ActivityItem(Icons.Default.Notifications, "Deployment Scheduled", "HoloSync · v0.3.0-beta", "3h ago", WarningAmber),
    ActivityItem(Icons.Default.Star, "Milestone Reached", "Neural Interface · 80% complete", "5h ago", NeonGreen),
)
