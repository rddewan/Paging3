package com.richarddewan.todo_paging3.data.paging.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize


/*
created by Richard Dewan 17/03/2021
*/
@Parcelize
data class TaskPaging(
    val totalPage: Int = 0,
    val currentPage: Int = 0,
    val tasks: List<Task>
): Parcelable {

    @IgnoredOnParcel
    val endOfPage = totalPage == currentPage // check if its a end of page

    @Parcelize
    data class Task(
        @SerializedName("id")
        val id: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("title")
        val title: String,
        @SerializedName("body")
        val body: String,
        @SerializedName("note")
        val note: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("updated_at")
        val updatedAt: String
    ): Parcelable

}