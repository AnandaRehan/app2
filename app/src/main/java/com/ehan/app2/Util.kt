package com.ehan.app2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TimeoutHelper(
    private val scope: CoroutineScope,
    private val dispatchers: Dispatchers
) {

    fun setTimeout(
        delayMs: Long,
        action: () -> Unit
    ): Job {
        return scope(dispatchers.Main).launch {
            delay(delayMs)
            action()
        }
    }

    fun clearTimeout(job: Job?) {
        job?.cancel()
    }
}
