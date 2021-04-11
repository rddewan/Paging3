package com.richarddewan.todo_paging3.di.component

import com.richarddewan.todo_paging3.di.module.ActivityModule
import com.richarddewan.todo_paging3.di.scope.ActivityScope
import com.richarddewan.todo_paging3.ui.MainActivity
import dagger.Component


/*
created by Richard Dewan 13/03/2021
*/

@ActivityScope
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    /*
    we need to write a function which takes the class that need dependencies
     */
    fun inject(activity: MainActivity)

}