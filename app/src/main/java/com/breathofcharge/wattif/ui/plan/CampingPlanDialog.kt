package com.breathofcharge.wattif.ui.plan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.breathofcharge.wattif.ui.components.LabelValue
import com.breathofcharge.wattif.ui.components.PrimaryButton
import com.breathofcharge.wattif.ui.components.SecondaryButton

@Composable
fun CampingPlanDialog(
    isEditing: Boolean,
    onDismiss: () -> Unit,
    onStart: () -> Unit,
) {
    var hasDestination by remember { mutableStateOf(isEditing) }
    var reserveBattery by remember { mutableFloatStateOf(20f) }
    val travelBattery = if (hasDestination) 14 else 0
    val availableBattery = 82 - travelBattery - reserveBattery.toInt()

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.widthIn(min = 560.dp, max = 760.dp),
        shape = RoundedCornerShape(36.dp),
        title = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("CAMPING PLAN", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("캠핑 계획 설정", style = MaterialTheme.typography.headlineLarge)
                Text(
                    if (hasDestination) {
                        "목적지 이동 소모량을 반영해 사용할 배터리를 계산해요."
                    } else {
                        "남길 배터리를 정하고, 필요할 때 다음 목적지를 추가할 수 있어요."
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                InfoField("캠핑 이름 · 선택", "한강 노을 캠핑")
                if (hasDestination) {
                    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)) {
                        Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            LabelValue("다음 목적지", "서울 성수동")
                            SecondaryButton(
                                text = "삭제",
                                onClick = { hasDestination = false },
                            )
                        }
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Row(Modifier.fillMaxWidth().padding(20.dp), horizontalArrangement = Arrangement.spacedBy(80.dp)) {
                            LabelValue("목적지까지", "84 km", Modifier.weight(1f))
                            LabelValue("이동 예상 소모", "약 14%", Modifier.weight(1f))
                        }
                    }
                } else {
                    SecondaryButton("＋  다음 목적지 추가", { hasDestination = true }, Modifier.fillMaxWidth())
                }
                Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)) {
                    Column(Modifier.padding(20.dp)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(if (hasDestination) "도착 후 남길 배터리" else "남길 배터리")
                            Text("${reserveBattery.toInt()}%", fontWeight = FontWeight.Bold)
                        }
                        Slider(
                            value = reserveBattery,
                            onValueChange = { reserveBattery = it },
                            valueRange = 10f..50f,
                            steps = 39,
                        )
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("10%", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("권장 20%", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("50%", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
                Surface(
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    color = Color.Transparent,
                    shape = RoundedCornerShape(22.dp),
                ) {
                    Row(Modifier.fillMaxWidth().padding(22.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        LabelValue("캠핑에 사용 가능한 배터리", "$availableBattery%")
                        LabelValue("현재", "82%")
                        if (hasDestination) LabelValue("캠핑 종료", "${82 - availableBattery}%")
                        LabelValue(if (hasDestination) "목적지 도착" else "캠핑 종료", "${reserveBattery.toInt()}%")
                    }
                }
                Spacer(Modifier.height(2.dp))
            }
        },
        confirmButton = {
            PrimaryButton(if (isEditing) "변경 내용 저장" else "캠핑 시작", onStart, Modifier.fillMaxWidth(), enabled = availableBattery > 0)
        },
        dismissButton = {},
    )
}

@Composable
private fun InfoField(
    label: String,
    value: String,
) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.fillMaxWidth().padding(20.dp)) {
            Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(6.dp))
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
