package com.example.productdisplayapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.productdisplayapp.uimodel.StyleUiModel
import kotlin.math.max
import kotlin.math.min

@Composable
fun StyleComponent(
    styleList: List<StyleUiModel>,
    onContentClick:(String) -> Unit,
    modifier: Modifier = Modifier
) {
    FirstItemSpanGrid(
        columns = 3,
        modifier = modifier
            .wrapContentHeight()
            .heightIn(max = 2000.dp)
    ) {
        styleList.forEach { style ->
            ContentImageItem(
                content = style,
                onImageClick = { onContentClick(style.linkURL) }
            )
        }
    }
}

const val GRID_SPAN_FIRST = 2
const val GRID_SPAN_DEFAULT = 1

@Composable
fun FirstItemSpanGrid(
    columns: Int,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    check(columns > 0) { "Columns must be greater than 0" }
    Layout(
        content = { content() },
        modifier = modifier
    ) { measurables, constraints ->

        val spans = List(measurables.size) { index ->
            // 첫번째 아이템에 span 적용
            if (index == 0) GRID_SPAN_FIRST else GRID_SPAN_DEFAULT
        }

        val baseWidth = constraints.maxWidth / columns
        val baseHeight = 800

        val cellConstraints = spans.map {
            Constraints.fixed(
                width = baseWidth * it,
                height = baseHeight * it
            )
        }

        val placeables = measurables.mapIndexed { index, measurable ->
            measurable.measure(cellConstraints[index])
        }

        layout(
            width = constraints.maxWidth,
            height = constraints.maxHeight
        ) {
            var x = 0
            var y = 0
            var spanScopeX = 0
            var spanScopeY = 0

            var currentColumnSpan = 0
            var currentRowSpan = 0

            val extraGridColumn = columns - GRID_SPAN_FIRST // span 제외한 컬럼 수

            placeables.forEachIndexed { index, placeable ->
                val span = min(spans[index], columns)

                if (extraGridIdxList(columns, GRID_SPAN_FIRST).contains(index)) {
                    if (index % (extraGridColumn) == 1) {
                        // 잔여 grid 처리 영역의 첫 시작 부분일 경우, x, y 초기화
                        spanScopeX = GRID_SPAN_FIRST * baseWidth
                        spanScopeY = (index / (extraGridColumn)) * baseHeight
                    } else{
                        if ((extraGridColumn) == 1) {
                            // 잔여 grid 처리 영역이 1일 경우
                            spanScopeX = GRID_SPAN_FIRST * baseWidth
                            spanScopeY = (index - 1) * baseHeight
                        } else {
                            // 잔여 grid 처리 영역이 1 이상일 경우, x값 누적
                            spanScopeX += span * baseWidth
                        }
                    }

                    placeable.placeRelative(
                        x = spanScopeX,
                        y = spanScopeY,
                    )

                    // span 영역 다음 라인 계산을 위해 설정하는 값
                    x = 0
                    currentColumnSpan = columns + 1
                } else {
                    // span 영역 다음 라인부터 리스트 처리
                    if (currentColumnSpan > columns) {
                        currentColumnSpan = span
                        x = 0
                        y += currentRowSpan * baseHeight
                        currentRowSpan = span
                    } else {
                        currentColumnSpan += span
                        currentRowSpan = max(currentRowSpan, span)
                    }

                    placeable.placeRelative(
                        x = x,
                        y = y,
                    )
                    x += span * baseWidth
                }
            }
        }
    }
}

// span이 적용된 전체 리스트 중 첫번째 값(span 영역)을 제외한 인덱스 리스트
fun extraGridIdxList(column: Int, span: Int): List<Int> {
    return (1 .. span * (column - span)).toList()
}
