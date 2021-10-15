package com.test.graphapp.utils

import java.util.*

fun <T> executeMovingMedian(rawData: Map<T, Long>, size: Int): Map<T, Long> {
    val alignedData = mutableMapOf<T, Long>()
    val firstValue = rawData.values.firstOrNull() ?: 0L
    val window = ArrayDeque(Collections.nCopies(size, firstValue))
    val rawQue = ArrayDeque(rawData.values)

    rawData.forEach { entry ->
        val first = rawQue.poll() ?: return@forEach

        window.addLast(first)
        window.poll()

        alignedData[entry.key] = window.sum() / size
    }

    return alignedData
}
