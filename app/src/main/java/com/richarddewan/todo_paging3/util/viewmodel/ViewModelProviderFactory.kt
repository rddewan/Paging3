package com.richarddewan.todo_paging3.util.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass


/*
created by Richard Dewan 13/03/2021
*/

class ViewModelProviderFactory<T: ViewModel> (
    private val kClass: KClass<T>,
    private val creator: () -> T
        ) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(kClass.java)){
            return creator() as T
        }

        throw IllegalArgumentException("Unknown class")
    }

}