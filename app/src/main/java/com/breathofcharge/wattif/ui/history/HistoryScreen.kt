package com.breathofcharge.wattif.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.breathofcharge.wattif.ui.model.CampingRecord
import com.breathofcharge.wattif.ui.model.sampleRecords

@Composable
fun HistoryScreen(
    onRecordClick: (CampingRecord) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        val compact = maxWidth < 480.dp
        if (compact) {
            Column(verticalArrangement = Arrangement.spacedBy(22.dp)) {
                HistoryList(onRecordClick, Modifier.fillMaxWidth().weight(1f))
                Row(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                ) {
                    MonthlySummary(Modifier.weight(1f).fillMaxHeight())
                    FavoriteMode(Modifier.weight(1f).fillMaxHeight())
                }
            }
        } else {
            Row(horizontalArrangement = Arrangement.spacedBy(28.dp)) {
                HistoryList(onRecordClick, Modifier.weight(1.8f).fillMaxHeight())
                Column(modifier = Modifier.weight(1f).fillMaxHeight(), verticalArrangement = Arrangement.spacedBy(22.dp)) {
                    MonthlySummary(Modifier.weight(1f).fillMaxWidth())
                    FavoriteMode(Modifier.weight(1f).fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun HistoryList(
    onRecordClick: (CampingRecord) -> Unit,
    modifier: Modifier,
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(Modifier.padding(28.dp)) {
            Text("기록", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(22.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                items(sampleRecords) { record ->
                    HistoryRow(record, onClick = { onRecordClick(record) })
                }
            }
        }
    }
}

@Composable
private fun MonthlySummary(modifier: Modifier) {
    Surface(modifier = modifier, color = Color.Transparent, shape = RoundedCornerShape(30.dp)) {
        androidx.compose.foundation.layout.Box(
            Modifier.fillMaxSize().padding(28.dp),
        ) {
            Column {
                Text("이번 달 휴식", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("42h", style = MaterialTheme.typography.displayLarge)
                Text("4번의 캠핑", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.weight(1f))
                Text("평균 10h 48m", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
private fun FavoriteMode(modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(Modifier.padding(28.dp)) {
            Text("가장 오래 사용한 모드", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(16.dp))
            Text("휴식 모드", style = MaterialTheme.typography.headlineLarge)
            Text("총 26시간", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun HistoryRow(
    record: CampingRecord,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(22.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 22.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Text(record.date, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Column(Modifier.weight(1f)) {
                Text(record.name, style = MaterialTheme.typography.titleLarge)
                Text(record.summary, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(record.duration, style = MaterialTheme.typography.titleMedium)
            Text(record.batteryChange, style = MaterialTheme.typography.titleLarge)
        }
    }
}
