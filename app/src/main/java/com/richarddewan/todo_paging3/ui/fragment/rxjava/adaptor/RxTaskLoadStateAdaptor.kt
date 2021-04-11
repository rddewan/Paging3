package com.richarddewan.todo_paging3.ui.fragment.rxjava.adaptor

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter


/*
created by Richard Dewan 18/03/2021
*/

class RxTaskLoadStateAdaptor(
    private val retry: () -> Unit) : LoadStateAdapter<RxTaskLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: RxTaskLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RxTaskLoadStateViewHolder {
        return RxTaskLoadStateViewHolder.create(parent,retry)
    }
}