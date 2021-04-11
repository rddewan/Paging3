package com.richarddewan.todo_paging3.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.richarddewan.todo_paging3.BaseApplication
import com.richarddewan.todo_paging3.R
import com.richarddewan.todo_paging3.di.component.DaggerFragmentComponent
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.di.module.FragmentModule
import javax.inject.Inject


/*
created by Richard Dewan 12/03/2021
*/

abstract class BaseFragment<VM : BaseViewModel, VB: ViewBinding> : Fragment() {

    @Inject
    lateinit var viewModel: VM
    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        //check network connection
        viewModel.checkNetworkConnection()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = provideLayout() as VB
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setup view
        setupView(view)
        //observe live data
        observers()

    }

    private fun observers() {
        //setup observer
        setupObservers()

        //check not network connection when fragment is created
        viewModel.isNetworkConnection.observe(viewLifecycleOwner, {
            if (!it) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.error_no_internet_connection),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    protected fun showErrorSnackBar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_INDEFINITE)
            .apply {
                setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red))
                setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                setActionTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                setAction(R.string.action_snackbar_close) {
                    dismiss()
                }
            }.show()
    }

    //build dagger component
    private fun buildFragmentComponent(): FragmentComponent {
        return DaggerFragmentComponent
            .builder()
            .fragmentModule(FragmentModule(this))
            .applicationComponent(
                (requireActivity().application as BaseApplication)
                    .applicationComponent
            )
            .build()
    }

    protected abstract fun provideLayout(): ViewBinding

    protected abstract fun setupView(view: View)

    protected abstract fun setupObservers()

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)
}