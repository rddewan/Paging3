package com.richarddewan.todo_paging3.di.component

import androidx.paging.ExperimentalPagingApi
import com.richarddewan.todo_paging3.di.module.FragmentModule
import com.richarddewan.todo_paging3.di.scope.FragmentScope
import com.richarddewan.todo_paging3.ui.fragment.flow.FlowFragment
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.FlowRemoteMediatorFragment
import com.richarddewan.todo_paging3.ui.fragment.home.HomeFragment
import com.richarddewan.todo_paging3.ui.fragment.rxjava.RxJavaFragment
import com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.RxJavaRemoteMediatorFragment
import dagger.Component


/*
created by Richard Dewan 13/03/2021
*/

@FragmentScope
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {

    /*
    we need to write a function which takes the class that need dependencies
     */
    fun inject(fragment: HomeFragment)

    fun inject(fragment: FlowFragment)

    fun inject(fragment: RxJavaFragment)

    fun inject(fragment: FlowRemoteMediatorFragment)

    @ExperimentalPagingApi
    fun inject(fragment: RxJavaRemoteMediatorFragment)


}