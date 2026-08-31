package com.ehan.app2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
