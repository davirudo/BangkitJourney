package com.example.katahatiplus

import com.example.katahatiplus.response.ListStoryItem
import com.example.katahatiplus.response.StoriesResponse

object DataDummy {

    fun generateDummyStoriesResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val stories = ListStoryItem(
                i.toString(),
                "photoUrl + $i",
                "createdAt + $i",
                "name $i",
                "description $i",
                0.0,
                0.0
            )
            items.add(stories)
        }
        return items
    }

}