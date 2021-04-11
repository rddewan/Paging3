package com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.databinding.TaskLoadStateFooterViewItemBinding


/*
created by Richard Dewan 10/04/2021
*/

class FlowTaskRemoteMediatorLoadStateViewHolder(
    private val binding: TaskLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener {
            retry.invoke() //its same as retry()
        }
    }

    fun bind(loadState: LoadState) {
        when (loadState) {
            is LoadState.NotLoading -> {
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.progressBar.isVisible = false
            }
            is LoadState.Loading -> {
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.progressBar.isVisible = true
            }
            is LoadState.Error -> {
                binding.errorMsg.text = loadState.error.message
                binding.retryButton.isVisible = true
                binding.errorMsg.isVisible = true
                binding.progressBar.isVisible = false
            }
        }
    }

    companion object {
        //create the instance of FlowTaskRemoteMediatorLoadStateViewHolder
        fun create(
            parent: ViewGroup,
            retry: () -> Unit
        ): FlowTaskRemoteMediatorLoadStateViewHolder {
            //create view
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_load_state_footer_view_item,parent,false)
            //bind the view
            val binding = TaskLoadStateFooterViewItemBinding.bind(view)
            //return the RecyclerView.ViewHolder
            return FlowTaskRemoteMediatorLoadStateViewHolder(binding,retry)
        }
    }
}