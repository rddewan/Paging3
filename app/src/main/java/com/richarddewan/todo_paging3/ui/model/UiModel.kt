package com.richarddewan.todo_paging3.ui.model

import com.richarddewan.todo_paging3.data.local.entity.TaskEntity

sealed class UiModel {
    data class TaskItem(val task: TaskEntity) : UiModel()
    data class SeparatorItem(val status: String) : UiModel()
}
