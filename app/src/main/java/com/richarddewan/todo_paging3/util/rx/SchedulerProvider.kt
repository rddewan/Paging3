package com.richarddewan.todo_paging3.util.rx

import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Singleton


/*
created by Richard Dewan 12/03/2021
*/

@Singleton
interface SchedulerProvider  {
    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}