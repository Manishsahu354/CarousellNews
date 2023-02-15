package com.example.carousellnews.utils

import com.example.carousellnews.data.model.NewsResponseModelItem
import java.util.concurrent.TimeUnit
import kotlin.Comparator

class Utility {

    companion object{
        val popularSortComparator = object : Comparator<NewsResponseModelItem>{
            override fun compare(p0: NewsResponseModelItem, p1: NewsResponseModelItem): Int {
                if (p0.rank == p1.rank){
                    return (p1.timeCreated - p0.timeCreated).toInt()
                }
                return p0.rank - p1.rank
            }
        }

        val times: List<Long> = listOf(
            TimeUnit.DAYS.toMillis(365),
            TimeUnit.DAYS.toMillis(30),
            TimeUnit.DAYS.toMillis(7),
            TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1)
        )

        private val timesString: List<String> = listOf("year", "month","week", "day", "hour", "minute", "second")

        fun readableFormatOfData(duration: Long): String {
            val res = StringBuffer()
            for (i in times.indices) {
                val current: Long = times[i]
                val temp = (System.currentTimeMillis() -duration) / current
                if (temp > 0) {
                    res.append(temp).append(" ").append(timesString[i])
                        .append(if (temp != 1L) "s" else "").append(" ago")
                    break
                }
            }
            return if ("" == res.toString()) "0 seconds ago" else res.toString()
        }
    }
}