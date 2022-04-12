package com.oseong.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import com.oseong.layouts.ui.theme.LayoutsTheme


@Composable
fun LargeConstraintLayout() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val text = createRef()
        val guideline = createGuidelineFromStart(0.5f)
        Text(
            "This is a very very very very very very very long This is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long textThis is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, parent.end)
                width = Dimension.preferredValue(0.dp)
            }
        )
    }
}

/*
Available Dimension behaviors are:
preferredWrapContent - the layout is wrap content, subject to the constraints in that dimension.
wrapContent - the layout is wrap content even if the constraints would not allow it.
fillToConstraints - the layout will expand to fill the space defined by its constraints in that dimension.
preferredValue - the layout is a fixed dp value, subject to the constraints in that dimension.
value - the layout is a fixed dp value, regardless of the constraints in that dimension
 */

/*
@Composable
fun LargeConstraintLayout() {
    ConstraintLayout {
        val text = createRef()

        val guideline = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "This is a very very very very very very very long text",
            Modifier.constrainAs(text) {
                linkTo(start = guideline, end = parent.end)
            }
        )
    }
}
*/

@Preview
@Composable
fun LargeConstraintLayoutPreview() {
    LayoutsTheme {
        LargeConstraintLayout()
    }
}