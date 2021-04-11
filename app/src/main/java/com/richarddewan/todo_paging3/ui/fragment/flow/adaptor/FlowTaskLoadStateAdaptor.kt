package com.richarddewan.todo_paging3.ui.fragment.flow.adaptor

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


/*
created by Richard Dewan 19/03/2021
*/

class FlowTaskLoadStateAdaptor(
    private val retry: () -> Unit
): LoadStateAdapter<FlowTaskLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: FlowTaskLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FlowTaskLoadStateViewHolder {
        return FlowTaskLoadStateViewHolder.create(parent,retry)
    }
}