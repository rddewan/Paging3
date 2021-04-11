package com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.databinding.SeparatorViewItemBinding
import com.richarddewan.todo_paging3.databinding.TaskListViewBinding
import com.richarddewan.todo_paging3.ui.model.UiModel


/*
created by Richard Dewan 09/04/2021
*/

class FlowTaskRemoteMediatorUiModelAdaptor : PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(
    diffCallback = diffUtil
){
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(
                oldItem: UiModel,
                newItem: UiModel
            ): Boolean {
                return (oldItem is UiModel.TaskItem && newItem is UiModel.TaskItem) &&
                        oldItem.task.id == newItem.task.id ||
                        (oldItem is UiModel.SeparatorItem && newItem is UiModel.SeparatorItem) &&
                        oldItem.status == newItem.status
            }

            override fun areContentsTheSame(
                oldItem: UiModel,
                newItem: UiModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            when(it) {
                is UiModel.TaskItem -> (holder as TaskRemoteMediatorViewHolder).onBind(it.task)
                is UiModel.SeparatorItem -> (holder as SeparatorViewHolder).onBind(it.status)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.task_list_view) {
            TaskRemoteMediatorViewHolder.create(parent)
        }else {
            SeparatorViewHolder.create(parent)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return  when(getItem(position)) {
            is UiModel.TaskItem -> R.layout.task_list_view
            is UiModel.SeparatorItem -> R.layout.separator_view_item
            null -> {
                throw UnsupportedOperationException("Unknown view")
            }
        }
    }


    class TaskRemoteMediatorViewHolder(
        val binding: TaskListViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: TaskEntity) {
            binding.lbTaskId.text = data.taskId.toString()
            binding.lbUserId.text = data.userId
            binding.lbTitle.text = data.title
            binding.lbBody.text = data.body
            binding.lbNote.text = data.note
            binding.lbStatus.text = data.status
        }

        companion object {
            fun create(parent: ViewGroup) : TaskRemoteMediatorViewHolder {
                val view  = LayoutInflater.from(parent.context)
                    .inflate(R.layout.task_list_view,parent, false)

                val binding = TaskListViewBinding.bind(view)

                return TaskRemoteMediatorViewHolder(binding)
            }
        }
    }

    class SeparatorViewHolder(
        private val binding: SeparatorViewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(description: String) {
            binding.separatorDescription.text = description
        }

        companion object {
            fun create(parent: ViewGroup): SeparatorViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.separator_view_item,  parent,false)

                val binding = SeparatorViewItemBinding.bind(view)

                return SeparatorViewHolder(binding)
            }
        }
    }



}