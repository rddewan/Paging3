package com.richarddewan.todo_paging3.ui.fragment.flow.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.databinding.TaskLoadStateFooterViewItemBinding


/*
created by Richard Dewan 19/03/2021
*/

class FlowTaskLoadStateViewHolder(
    private val binding: TaskLoadStateFooterViewItemBinding,
    private val retry: () ->Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(loadState: LoadState) {
        when(loadState) {
            is LoadState.NotLoading -> {
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
            }
            is LoadState.Loading -> {
                binding.progressBar.isVisible = true
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
            }
            is LoadState.Error -> {
                binding.errorMsg.text = loadState.error.localizedMessage
                binding.progressBar.isVisible = false
                binding.retryButton.isVisible = true
                binding.errorMsg.isVisible = true
            }
        }
    }

    companion object {

        fun create(parent: ViewGroup, retry: () -> Unit) : FlowTaskLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_load_state_footer_view_item,parent, false)
            val binding = TaskLoadStateFooterViewItemBinding.bind(view)

            return FlowTaskLoadStateViewHolder(binding, retry)
        }

    }
}