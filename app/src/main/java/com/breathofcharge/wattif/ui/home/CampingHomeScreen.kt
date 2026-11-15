package com.breathofcharge.wattif.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.breathofcharge.wattif.ui.components.MetricCard
import com.breathofcharge.wattif.ui.components.PrimaryButton
import com.breathofcharge.wattif.ui.components.ProgressBar
import com.breathofcharge.wattif.ui.components.SecondaryButton
import com.breathofcharge.wattif.ui.model.CampingPreset
import com.breathofcharge.wattif.ui.model.VehicleSummary
import com.breathofcharge.wattif.ui.model.WeatherSummary
import com.breathofcharge.wattif.ui.theme.WattIfTheme

@Composable
fun CampingHomeScreen(
    isActive: Boolean,
    selectedPreset: CampingPreset,
    onPresetSelected: (CampingPreset) -> Unit,
    onPlanClick: () -> Unit,
    onEndClick: () -> Unit,
    onControlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(modifier.fillMaxSize()) {
        if (maxWidth < 480.dp) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                CampingSummaryPanel(isActive, onPlanClick, onEndClick, Modifier.fillMaxWidth().height(660.dp))
                CampingControls(selectedPreset, onPresetSelected, onControlClick, Modifier.fillMaxWidth())
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(28.dp),
            ) {
                CampingSummaryPanel(isActive, onPlanClick, onEndClick, Modifier.weight(1.08f).fillMaxHeight())
                CampingControls(
                    selectedPreset,
                    onPresetSelected,
                    onControlClick,
                    Modifier.weight(1f).fillMaxHeight().verticalScroll(rememberScrollState()),
                )
            }
        }
    }
}

@Composable
private fun CampingControls(
    selectedPreset: CampingPreset,
    onPresetSelected: (CampingPreset) -> Unit,
    onControlClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text("간편 프리셋", style = MaterialTheme.typography.headlineMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            CampingPreset.entries.forEach { preset ->
                PresetCard(preset, selectedPreset == preset, { onPresetSelected(preset) }, Modifier.weight(1f))
            }
        }
        Text("개별 설정", style = MaterialTheme.typography.headlineMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            ControlCard("온도", "22°C", 0.62f, Modifier.weight(1f)) { onControlClick("온도") }
            ControlCard("조명", "20%", 0.38f, Modifier.weight(1f)) { onControlClick("조명") }
            ControlCard("시트", "3", 0.52f, Modifier.weight(1f)) { onControlClick("시트") }
            ControlCard("디스플레이", "60%", 0.72f, Modifier.weight(1f)) { onControlClick("디스플레이") }
        }
    }
}

@Composable
private fun CampingSummaryPanel(
    isActive: Boolean,
    onPlanClick: () -> Unit,
    onEndClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vehicle = VehicleSummary(batteryPercent = if (isActive) 64 else 82, drivingRangeKm = if (isActive) 248 else 304)
    val weather = WeatherSummary()
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(32.dp),
        color = Color.Transparent,
    ) {
        Box(
            modifier =
                Modifier.fillMaxSize().then(
                    Modifier,
                ),
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent,
                shape = RoundedCornerShape(32.dp),
            ) {
                Box(
                    Modifier.fillMaxSize().padding(30.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(22.dp),
                    ) {
                        Text(if (isActive) "한강 노을 캠핑" else "캠핑 계획", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text(
                            if (isActive) "2시간 18분" else "캠핑 준비를 시작해볼까요?",
                            style = if (isActive) MaterialTheme.typography.displayLarge else MaterialTheme.typography.headlineLarge,
                        )
                        if (isActive) {
                            SecondaryButton("캠핑 정보 수정", onPlanClick)
                            Text("캠핑에 사용할 배터리", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("40%", style = MaterialTheme.typography.titleLarge)
                                Text("18% 사용", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            ProgressBar(0.4f, Modifier.fillMaxWidth())
                        } else {
                            PrimaryButton("캠핑 계획 설정", onPlanClick)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            MetricCard("현재 배터리", "${vehicle.batteryPercent}%", Modifier.weight(1f))
                            MetricCard("주행 가능 거리", "${vehicle.drivingRangeKm} km", Modifier.weight(1f))
                            MetricCard("실내 온도", "${vehicle.indoorTemperature}°C", Modifier.weight(1f))
                        }
                        if (isActive) {
                            Surface(color = MaterialTheme.colorScheme.surface.copy(alpha = 0.82f), shape = RoundedCornerShape(20.dp)) {
                                Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Column {
                                        Text("다음 목적지", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        Text("남양주", style = MaterialTheme.typography.titleLarge)
                                    }
                                    Text(
                                        "84 km · 42% 확보",
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    )
                                }
                            }
                        }
                        Text("현재 날씨 · ${weather.location}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Text("${weather.condition}  ${weather.temperature}°", style = MaterialTheme.typography.displayLarge)
                        Text("오늘 최저 ${weather.minimumTemperature}°C", style = MaterialTheme.typography.titleLarge)
                        Text("강수 ${weather.rainChance}% · 바람 ${weather.windSpeed}m/s", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (isActive) PrimaryButton("캠핑 종료", onEndClick, Modifier.fillMaxWidth())
                        Spacer(Modifier.height(2.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PresetCard(
    preset: CampingPreset,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.height(190.dp).clickable(onClick = onClick),
        color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
        contentColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(preset.title, style = MaterialTheme.typography.titleLarge)
            Text(
                preset.description,
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) Color.White.copy(alpha = 0.75f) else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(Modifier.weight(1f))
            Text(if (selected) "적용 중" else "적용하기", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ControlCard(
    label: String,
    value: String,
    level: Float,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.height(250.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(Modifier.padding(18.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(14.dp))
            Box(Modifier.width(12.dp).weight(1f), contentAlignment = Alignment.BottomCenter) {
                Surface(Modifier.fillMaxSize(), shape = RoundedCornerShape(8.dp), color = MaterialTheme.colorScheme.surfaceVariant) {}
                Surface(
                    Modifier.fillMaxWidth().fillMaxHeight(level),
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primary,
                ) {}
            }
            Spacer(Modifier.height(14.dp))
            Text(label, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(widthDp = 1280, heightDp = 720)
@Composable
private fun CampingHomePreview() {
    WattIfTheme {
        CampingHomeScreen(false, CampingPreset.REST, {}, {}, {}, {})
    }
}
