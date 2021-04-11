package com.richarddewan.todo_paging3

import android.app.Application
import com.richarddewan.todo_paging3.di.component.ApplicationComponent
import com.richarddewan.todo_paging3.di.component.DaggerApplicationComponent
import com.richarddewan.todo_paging3.di.module.ApplicationModule


/*
created by Richard Dewan 15/03/2021
*/

class BaseApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        //inject dependencies
        buildApplicationComponent()
    }

    private fun buildApplicationComponent(){
       applicationComponent= DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
           .build()

        applicationComponent.inject(this)
    }
}