package com.example.todo_app.workmanagerutils

import android.content.Context
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.util.concurrent.TimeUnit

class WorkManagerService(val context: Context) {

    fun schedule(name: String, delay: Long) {
        val constraint = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = OneTimeWorkRequestBuilder<NotificationWorker>()
            .addTag(name)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setConstraints(constraint)
            .setInputData(workDataOf("name" to name))
            .build()

        WorkManager.getInstance(context).enqueue(request)
    }
}