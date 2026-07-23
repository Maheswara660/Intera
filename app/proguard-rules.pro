# =============================================================
#  Intera — ProGuard / R8 Rules
#  Jetpack Compose · Navigation · Material 3 · AppCompat
#  Target: R8 full-mode  |  AGP 9.x  |  compileSdk 37.0
# =============================================================

# ── Output / Debugging ───────────────────────────────────────
# Preserve source file names and line numbers so crash stack
# traces in production remain human-readable.
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile

# ── Kotlin ───────────────────────────────────────────────────
-keep class kotlin.Metadata { *; }
-keep class kotlin.reflect.** { *; }
-dontwarn kotlin.**

# Kotlin sealed-class $WhenMappings helper arrays
-keepclassmembers class **$WhenMappings {
    <fields>;
}

# Kotlin companion objects
-keepclassmembers class * {
    public static ** Companion;
}

# ── Kotlin Coroutines ────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ── Jetpack Compose ──────────────────────────────────────────
# Compose relies on the Kotlin compiler plugin at compile time;
# at runtime it uses reflection to locate composable lambdas.
-keep class androidx.compose.** { *; }
-keep interface androidx.compose.** { *; }

# Keep all @Composable-annotated functions and their host classes
-keep @androidx.compose.runtime.Composable class * { *; }
-keepclassmembers class * {
    @androidx.compose.runtime.Composable <methods>;
}

# Compose runtime stability / @Stable / @Immutable
-keepclassmembers class * implements androidx.compose.runtime.Stable {
    <methods>;
}

# Compose compiler-generated classes (e.g. ComposableSingletons$...)
-keep class **ComposableSingletons* { *; }

# Compose UI / Canvas internals
-keep class androidx.compose.ui.** { *; }
-keep class androidx.compose.animation.** { *; }
-keep class androidx.compose.foundation.** { *; }

# ── Jetpack Navigation Compose ───────────────────────────────
-keep class * extends androidx.navigation.Navigator
-keepnames class androidx.navigation.** { *; }
-keep class androidx.navigation.compose.** { *; }

# ── Material Design 3 (Compose) ──────────────────────────────
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.material.** { *; }

# ── AndroidX Lifecycle ───────────────────────────────────────
-keep class androidx.lifecycle.** { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>();
}

# ── AndroidX Activity / AppCompat ────────────────────────────
-keep class androidx.activity.** { *; }
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**

# ── AndroidX Core ────────────────────────────────────────────
-keep class androidx.core.** { *; }
-dontwarn androidx.**

# ── App Data Models (Intera) ─────────────────────────────────
# Prevent R8 from stripping or renaming the data-layer classes
# used in Navigation Compose and screen composables.
-keep class com.maheswara660.intera.ui.screens.ProjectItem { *; }
-keep class com.maheswara660.intera.ui.screens.ActivityItem { *; }
-keep class com.maheswara660.intera.ui.theme.** { *; }
-keep class com.maheswara660.intera.** { *; }

# ── Android Framework Fundamentals ───────────────────────────
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Keep Parcelables
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Keep enums
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep all public R fields (accessed by View inflation, resources)
-keepclassmembers class **.R$* {
    public static <fields>;
}

# ── Suppress Harmless Warnings ───────────────────────────────
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**
-dontwarn com.google.errorprone.annotations.**
-dontwarn sun.misc.Unsafe
