package com.oseong.layouts

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oseong.layouts.ui.theme.LayoutsTheme

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business","Arts & Crafts", "Beauty", "Books"
)


@Composable
fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Keep track of the width of each row
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows // 3일 경우-> 0,1,2 .. 0,1,2 반복
            rowWidths[row] += placeable.width
            rowHeights[row] = Math.max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        // Y of each row, based on the height accumulation of previous rows
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }

        // Set the size of the parent layout
        layout(width, height) {
            // x cord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                Log.d("test", "placeable.width ${placeable.width}")
                rowX[row] += placeable.width
                Log.d("test", "rowx ${rowX[row]}")
                Log.d("test", "rowy ${rowY[row]}")
            }
        }

    }
}

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    LayoutsTheme {
        Chip(text = "Hi there")
    }
}


@Preview
@Composable
fun MyStaggeredGridTest() {
    StaggeredGrid(modifier = Modifier) {
        for (topic in topics) {
            Chip(modifier = Modifier.padding(8.dp), text = topic)
        }
    }
}


@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        var itemMaxWidth = 0
        var itemsHeight = 0

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            itemsHeight += placeable.height

            itemMaxWidth = maxOf(itemMaxWidth, placeable.width)

            placeable
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(itemMaxWidth, itemsHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}

/*@Preview
@Composable
fun MyColumnTest(modifier: Modifier = Modifier) {
    MyOwnColumn(
        modifier
            .padding(8.dp)
            .background(Color.Yellow)
    ) {
        Text("MyOwnColumn")
        Text("places items")
        Text("vertically.")
        Text("We've done it by hand!")
    }
}*/

@Composable
fun MyOwnRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        var itemMaxHeight = 0
        var itemsWidth = 0

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            val placeable = measurable.measure(constraints)

            itemMaxHeight = maxOf(itemMaxHeight, placeable.height)

            itemsWidth += placeable.width

            placeable
        }

        var xPosition = 0

        layout(itemsWidth, itemMaxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = xPosition, y = 0)

                xPosition += placeable.width
            }
        }
    }
}

/*
@Preview
@Composable
fun MyRowTest(modifier: Modifier = Modifier) {
    MyOwnRow(
        modifier
            .padding(8.dp)
            .background(Color.Yellow)
    ) {
        Text(
            "hello", modifier = modifier
                .background(Color.Blue)
                .height(40.dp)
        )
        Text(
            "compose", modifier = modifier
                .background(Color.Red)
                .height(80.dp)
        )
        Text("horizontal.", modifier = modifier.background(Color.Green))
    }
}*/
