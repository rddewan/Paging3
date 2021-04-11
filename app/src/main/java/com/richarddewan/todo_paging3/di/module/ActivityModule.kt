package com.richarddewan.todo_paging3.di.module


import com.richarddewan.todo_paging3.ui.base.BaseActivity
import dagger.Module
import dagger.Provides


/*
created by Richard Dewan 13/03/2021
*/

@Module
class ActivityModule (private val activity: BaseActivity<*>) {

    @Provides
    fun provideContext() = activity


}