package com.richarddewan.todo_paging3.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity


/*
created by Richard Dewan 12/03/2021
*/

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}