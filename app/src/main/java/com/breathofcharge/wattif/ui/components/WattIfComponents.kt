package com.breathofcharge.wattif.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.breathofcharge.wattif.ui.theme.Ink

enum class MainTab { CAMPING, HISTORY }

@Composable
fun WattIfTopBar(
    selectedTab: MainTab,
    isNightMode: Boolean,
    onTabSelected: (MainTab) -> Unit,
    onNightModeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val compact = LocalConfiguration.current.screenWidthDp < 480
    if (compact) {
        Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text("WATT IF", style = MaterialTheme.typography.titleLarge)
                ConnectionStatus(isNightMode, onNightModeClick)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SegmentedTab("캠핑", selectedTab == MainTab.CAMPING, Modifier.weight(1f)) { onTabSelected(MainTab.CAMPING) }
                SegmentedTab("기록", selectedTab == MainTab.HISTORY, Modifier.weight(1f)) { onTabSelected(MainTab.HISTORY) }
            }
        }
        return
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text("WATT IF", style = MaterialTheme.typography.titleLarge)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            SegmentedTab("캠핑", selectedTab == MainTab.CAMPING) { onTabSelected(MainTab.CAMPING) }
            SegmentedTab("기록", selectedTab == MainTab.HISTORY) { onTabSelected(MainTab.HISTORY) }
        }
        ConnectionStatus(isNightMode, onNightModeClick)
    }
}

@Composable
private fun ConnectionStatus(
    isNightMode: Boolean,
    onNightModeClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(horizontalAlignment = Alignment.End) {
            Text("차량 연결됨", style = MaterialTheme.typography.labelLarge)
            Text("방금 업데이트", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Surface(
            modifier = Modifier.size(52.dp).clickable(onClick = onNightModeClick),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(if (isNightMode) "☀" else "☾", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
private fun SegmentedTab(
    label: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val horizontalPadding = if (LocalConfiguration.current.screenWidthDp < 480) 24.dp else 54.dp
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
        shape = RoundedCornerShape(28.dp),
    ) {
        Text(
            label,
            modifier = Modifier.padding(horizontal = horizontalPadding, vertical = 14.dp),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Surface(
        modifier = modifier.clickable(enabled = enabled, onClick = onClick),
        color = if (enabled) MaterialTheme.colorScheme.primary else Color(0xFFC6C6CC),
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(modifier = Modifier.padding(horizontal = 28.dp, vertical = 17.dp), contentAlignment = Alignment.Center) {
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        shape = RoundedCornerShape(24.dp),
    ) {
        Box(modifier = Modifier.padding(horizontal = 28.dp, vertical = 16.dp), contentAlignment = Alignment.Center) {
            Text(text, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun MetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.surface.copy(alpha = 0.86f), shape = RoundedCornerShape(18.dp)) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(8.dp))
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Composable
fun SectionCard(
    modifier: Modifier = Modifier,
    dark: Boolean = false,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = modifier,
        color = if (dark) Ink else MaterialTheme.colorScheme.surface,
        contentColor = if (dark) Color.White else MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(28.dp),
    ) {
        Box(modifier = Modifier.padding(28.dp)) { content() }
    }
}

@Composable
fun ProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.height(12.dp).background(MaterialTheme.colorScheme.outline, RoundedCornerShape(6.dp))) {
        Box(Modifier.fillMaxWidth(progress.coerceIn(0f, 1f)).height(12.dp).background(Ink, RoundedCornerShape(6.dp)))
    }
}

@Composable
fun LabelValue(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
    }
}
