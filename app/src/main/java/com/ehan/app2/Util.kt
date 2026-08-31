package com.ehan.app2

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TimeoutHelper {

    fun setTimeout(
        delayMs: Long,
        action: () -> Unit
    ): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            delay(delayMs)
            action()
        }
    }

    fun clearTimeout(job: Job?) {
        job?.cancel()
    }
}
