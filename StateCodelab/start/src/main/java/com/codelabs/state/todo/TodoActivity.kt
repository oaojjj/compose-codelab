/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codelabs.state.todo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.codelabs.state.ui.StateCodelabTheme

class TodoActivity : AppCompatActivity() {

    val todoViewModel by viewModels<TodoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StateCodelabTheme {
                Surface {
                    TodoActivityScreen(todoViewModel)
                }
            }
        }
    }
}


// 단방향 데이터 흐름 디자인
// ViewModel과 TodoScreen 사이의 다리 역할
@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
    // observeAsState -> LiveData<t>를 관찰하고 이를 State<T> 객체로 변환함
    //                   컴포저블이 컴포지션에서 제거되면 자동으로 관찰을 중지함
    val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(initial = listOf())
    TodoScreen(
        items = items,
        onAddItem = { todoViewModel.addItem(it) },
        onRemoveItem = { todoViewModel.removeItem(it) }
    )
}