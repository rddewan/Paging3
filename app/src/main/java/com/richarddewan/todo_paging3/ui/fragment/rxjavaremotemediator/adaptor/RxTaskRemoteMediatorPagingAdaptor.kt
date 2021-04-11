package com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.databinding.TaskListViewBinding


/*
created by Richard Dewan 10/04/2021
*/

class RxTaskRemoteMediatorPagingAdaptor :
    PagingDataAdapter<TaskEntity, RxTaskRemoteMediatorPagingAdaptor.RxRemoteMediatorTaskViewHolder>(
        diffCallback = diffUtil
    ) {

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<TaskEntity>() {
            override fun areItemsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return  oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TaskEntity, newItem: TaskEntity): Boolean {
                return  oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RxRemoteMediatorTaskViewHolder, position: Int) {
        getItem(position)?.let {
            holder.onBind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RxRemoteMediatorTaskViewHolder {
        val binding = TaskListViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)

       return RxRemoteMediatorTaskViewHolder(binding)
    }

    class RxRemoteMediatorTaskViewHolder(
        val binding: TaskListViewBinding
        ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: TaskEntity) {
            binding.lbTaskId.text = data.taskId.toString()
            binding.lbUserId.text = data.userId
            binding.lbTitle.text = data.title
            binding.lbBody.text = data.body
            binding.lbNote.text = data.note
            binding.lbStatus.text = data.status
        }

    }

}