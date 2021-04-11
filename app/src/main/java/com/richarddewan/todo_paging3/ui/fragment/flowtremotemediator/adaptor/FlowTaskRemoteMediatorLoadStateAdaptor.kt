package com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


/*
created by Richard Dewan 10/04/2021
*/

class FlowTaskRemoteMediatorLoadStateAdaptor(
    private val retry: () -> Unit): LoadStateAdapter<FlowTaskRemoteMediatorLoadStateViewHolder>(){

    override fun onBindViewHolder(
        holder: FlowTaskRemoteMediatorLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FlowTaskRemoteMediatorLoadStateViewHolder {
        return FlowTaskRemoteMediatorLoadStateViewHolder.create(parent,retry)
    }

}