package com.richarddewan.todo_paging3.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.databinding.FragmentHomeBinding
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.ui.base.BaseFragment


/*
created by Richard Dewan 11/03/2021
*/

class HomeFragment : BaseFragment<HomeViewModel,FragmentHomeBinding>() {

    override fun provideLayout(): ViewBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun setupView(view: View) {
        binding.btnCounter.setOnClickListener {
            viewModel.count.value = viewModel.count.value?.plus(1)
            viewModel.getUser()
        }
    }

    override fun setupObservers() {
        viewModel.count.observe(viewLifecycleOwner,{
            binding.lbCounter.text = it.toString()

        })

        viewModel.isNetworkAvailable.observe(viewLifecycleOwner, {
            if (!it) showErrorSnackBar(getString(R.string.error_no_internet_connection))
        })
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }


}

