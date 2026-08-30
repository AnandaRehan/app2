package com.ehan.app2

import android.app.Activity
import android.os.Build
import kotlinx.coroutines.*

class TimeoutHelper(private val scope: CoroutineScope) {

    fun setTimeout(delayMs: Long, action: () -> Unit): Job {
        return scope.launch {
            delay(delayMs)
            action()
        }
    }

    fun clearTimeout(job: Job?) {
        job?.cancel()
    }
}