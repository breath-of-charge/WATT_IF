package com.breathofcharge.wattif.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.breathofcharge.wattif.ui.components.LabelValue
import com.breathofcharge.wattif.ui.components.PrimaryButton
import com.breathofcharge.wattif.ui.components.SecondaryButton
import com.breathofcharge.wattif.ui.model.CampingRecord

@Composable
fun EndCampingDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    onDiscard: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("캠핑을 종료할까요?", style = MaterialTheme.typography.headlineMedium) },
        text = { Text("캠핑을 종료하면 차량 설정을 시작 전 상태로 복원합니다.", color = MaterialTheme.colorScheme.onSurfaceVariant) },
        confirmButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SecondaryButton("저장하지 않고 종료", onDiscard)
                PrimaryButton("종료하고 저장", onSave)
            }
        },
        dismissButton = { SecondaryButton("취소", onDismiss) },
        shape = RoundedCornerShape(28.dp),
    )
}

@Composable
fun CampingSummaryDialog(onContinue: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        modifier = Modifier.widthIn(min = 760.dp, max = 980.dp),
        title = {
            Column {
                Surface(
                    color = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("SAVED", modifier = Modifier.padding(horizontal = 22.dp, vertical = 10.dp), fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(20.dp))
                Text("오늘의 캠핑을 마쳤어요", style = MaterialTheme.typography.headlineLarge)
                Text("2026-11-13 · 한강 노을 캠핑", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Text("18%", style = MaterialTheme.typography.displayLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryMetric("캠핑 시간", "12h 34m", Modifier.weight(1f))
                    SummaryMetric("시작 잔량", "82%", Modifier.weight(1f))
                    SummaryMetric("종료 잔량", "64%", Modifier.weight(1f), dark = true)
                }
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)) {
                    Text("차량 설정 복원을 완료했어요.", modifier = Modifier.fillMaxWidth().padding(22.dp), style = MaterialTheme.typography.titleLarge)
                }
            }
        },
        confirmButton = { PrimaryButton("확인 완료", onContinue, Modifier.fillMaxWidth()) },
        shape = RoundedCornerShape(36.dp),
    )
}

@Composable
private fun SummaryMetric(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    dark: Boolean = false,
) {
    Surface(
        modifier = modifier,
        color = if (dark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        contentColor = if (dark) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(22.dp),
    ) {
        Column(Modifier.padding(22.dp)) {
            Text(
                label,
                color = if (dark) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(value, style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Composable
fun DepartureChecklistDialog(
    onLater: () -> Unit,
    onComplete: () -> Unit,
) {
    val checks = remember { mutableStateListOf(false, false, false) }
    val labels =
        listOf(
            "창문 · 도어 · 트렁크가 모두 닫혀 있어요",
            "충전 케이블과 외부 장비를 정리했어요",
            "소지품과 캠핑 장소를 다시 확인했어요",
        )
    AlertDialog(
        onDismissRequest = onLater,
        modifier = Modifier.widthIn(min = 760.dp, max = 980.dp),
        title = {
            Column {
                Text("출발 전 점검", style = MaterialTheme.typography.headlineLarge)
                Text("출발하기 전에 차량 상태와 주변을 확인해 주세요.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)) {
                    Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        LabelValue("다음 목적지", "서울 성수동")
                        LabelValue("목적지까지", "84 km")
                        LabelValue("현재 배터리", "64%")
                        LabelValue("도착 예상", "약 50%")
                    }
                }
                Text("차량에서 확인했어요", style = MaterialTheme.typography.titleLarge)
                Surface(border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("✓  차량 설정 복원                                 완료", fontWeight = FontWeight.Bold)
                        Text("✓  캠핑 모드 종료                                   완료", fontWeight = FontWeight.Bold)
                    }
                }
                Text("직접 확인해 주세요", style = MaterialTheme.typography.titleLarge)
                Surface(border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.fillMaxWidth().padding(12.dp)) {
                        labels.forEachIndexed { index, label ->
                            Row(
                                Modifier.fillMaxWidth().clickable { checks[index] = !checks[index] },
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Checkbox(checked = checks[index], onCheckedChange = { checks[index] = it })
                                Text(label)
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SecondaryButton("나중에", onLater, Modifier.weight(0.35f))
                PrimaryButton("완료", onComplete, Modifier.weight(0.65f), enabled = checks.all { it })
            }
        },
        shape = RoundedCornerShape(32.dp),
    )
}

@Composable
fun RecordDetailDialog(
    record: CampingRecord,
    onDismiss: () -> Unit,
    onChecklist: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.widthIn(min = 760.dp, max = 980.dp),
        title = {
            Column {
                Text(record.name, style = MaterialTheme.typography.headlineLarge)
                Text("11.04 23:20 – 11.05 07:20", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(22.dp)) {
                    Row(Modifier.fillMaxWidth().padding(22.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        LabelValue("캠핑 시간", "8시간")
                        LabelValue("총 배터리 사용", record.batteryChange)
                        LabelValue("배터리 변화", "82% → 64%")
                    }
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("다음 목적지  서울 성수동", fontWeight = FontWeight.Bold)
                    Text("84 km")
                }
                Text("사용한 모드", style = MaterialTheme.typography.titleLarge)
                Surface(border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ModeHistoryRow("23:20–00:40", "휴식 모드", "1시간 20분")
                        ModeHistoryRow("00:40–02:10", "미디어 모드", "1시간 30분")
                        ModeHistoryRow("02:10–03:05", "연결 끊김", "확인 불가")
                        ModeHistoryRow("03:05–07:20", "취침 모드", "4시간 15분")
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    SummaryMetric("차량 설정 복원", "완료", Modifier.weight(1f))
                    Surface(
                        modifier = Modifier.weight(1.3f).clickable(onClick = onChecklist),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(22.dp),
                    ) {
                        Row(
                            Modifier.padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            LabelValue("출발 전 점검", "미완료")
                            Text("점검하러 가기", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
        confirmButton = { SecondaryButton("닫기", onDismiss, Modifier.fillMaxWidth()) },
        shape = RoundedCornerShape(32.dp),
    )
}

@Composable
private fun ModeHistoryRow(
    time: String,
    mode: String,
    duration: String,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(time, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(mode, modifier = Modifier.weight(1f).padding(start = 42.dp), fontWeight = FontWeight.Bold)
        Text(duration, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

enum class DemoAlert { BATTERY, PRESET, VEHICLE }

@Composable
fun DemoAlertDialog(
    type: DemoAlert,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val content =
        when (type) {
            DemoAlert.BATTERY -> Triple("출발용 배터리를 남겨둘 시간이에요", "현재 42% · 설정한 기준에 도달했어요.", "캠핑 종료")
            DemoAlert.PRESET -> Triple("조명 설정을 적용하지 못했어요", "적용 전 설정으로 모두 복원했어요.", "전체 다시 시도")
            DemoAlert.VEHICLE -> Triple("차량 정보를 불러오지 못했어요", "배터리와 주행 가능 거리를 확인할 수 없습니다.", "다시 시도")
        }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(content.first, style = MaterialTheme.typography.headlineMedium) },
        text = { Text(content.second, color = MaterialTheme.colorScheme.onSurfaceVariant) },
        confirmButton = { PrimaryButton(content.third, onConfirm) },
        dismissButton = { SecondaryButton(if (type == DemoAlert.BATTERY) "계속 사용" else "취소", onDismiss) },
        shape = RoundedCornerShape(24.dp),
    )
}
