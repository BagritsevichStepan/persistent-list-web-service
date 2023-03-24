package com.wmms.testtask3async.util

import java.util.concurrent.Executors

import java.util.concurrent.ExecutorService


class ConcurrencyUtil {
    companion object {
        private val executorService: ExecutorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        )

        fun submit(task: Runnable) {
            executorService.submit(task)
        }

        fun shutdown(): MutableList<Runnable> = executorService.shutdownNow()
    }
}