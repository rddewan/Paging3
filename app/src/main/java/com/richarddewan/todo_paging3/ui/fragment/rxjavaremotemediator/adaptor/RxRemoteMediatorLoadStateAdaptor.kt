package com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.adaptor

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


/*
created by Richard Dewan 11/04/2021
*/

class RxRemoteMediatorLoadStateAdaptor(
    private val retry: () -> Unit
) : LoadStateAdapter<RxRemoteMediatorLoadStateViewHolder>() {

    override fun onBindViewHolder(
        holder: RxRemoteMediatorLoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RxRemoteMediatorLoadStateViewHolder {
        return RxRemoteMediatorLoadStateViewHolder.create(parent, retry)
    }
}