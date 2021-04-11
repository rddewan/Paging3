package com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.databinding.TaskLoadStateFooterViewItemBinding


/*
created by Richard Dewan 11/04/2021
*/

class RxRemoteMediatorLoadStateViewHolder(
    private val binding: TaskLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root){
    init {
        binding.retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun bind(state: LoadState) {
        when (state) {
            is LoadState.Loading -> {
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.progressBar.isVisible = true

            }
            is LoadState.NotLoading -> {
                binding.retryButton.isVisible = false
                binding.errorMsg.isVisible = false
                binding.progressBar.isVisible = false
            }
            is LoadState.Error -> {
                binding.errorMsg.text = state.error.message
                binding.retryButton.isVisible = true
                binding.errorMsg.isVisible = true
                binding.progressBar.isVisible = false

            }
        }
    }

    companion object {

        fun create(parent: ViewGroup, retry: () -> Unit): RxRemoteMediatorLoadStateViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.task_load_state_footer_view_item, parent, false)

            val binding = TaskLoadStateFooterViewItemBinding.bind(view)
            return RxRemoteMediatorLoadStateViewHolder(binding, retry)
        }
    }
}