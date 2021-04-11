package com.richarddewan.todo_paging3.ui.fragment.rxjava.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.databinding.TaskListViewBinding


/*
created by Richard Dewan 18/03/2021
*/

class RxTaskPagingAdaptor: PagingDataAdapter<TaskPaging.Task,RxTaskPagingAdaptor.TaskViewHolder>(
    diffCallback = diffUtil
) {

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding  = TaskListViewBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return TaskViewHolder(binding)
    }

    class TaskViewHolder(val binding: TaskListViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: TaskPaging.Task) {
            binding.lbTaskId.text = data.id
            binding.lbUserId.text = data.userId
            binding.lbTitle.text = data.title
            binding.lbBody.text = data.body
            binding.lbNote.text = data.note
            binding.lbStatus.text = data.status
        }
    }

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<TaskPaging.Task>(){
            override fun areItemsTheSame(
                oldItem: TaskPaging.Task,
                newItem: TaskPaging.Task
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TaskPaging.Task,
                newItem: TaskPaging.Task
            ): Boolean {
                return oldItem == newItem
            }

        }
    }


}