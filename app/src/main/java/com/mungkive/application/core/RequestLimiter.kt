package com.mungkive.application.core

import android.util.Log
import kotlin.collections.ArrayDeque

class RequestLimiter(
    private val tag: String,
    private val maxCalls: Int,
    private val windowMillis: Long
) {
    private val q = ArrayDeque<Long>(maxCalls)

    @Synchronized
    fun tryAcquire(): Boolean {
        val now = System.currentTimeMillis()

        while (q.isNotEmpty() && now - q.first() > windowMillis) {
            q.removeFirst()
        }

        if (q.size < maxCalls) {
            q.addLast(now)
            return true
        }

        val last = q.last()
        Log.i(tag, "요청 한도 초과 (마지막 요청: ${fmt(last)})")
        return false
    }

    private fun fmt(ts: Long) =
        android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", ts).toString()
}
