package com.test.graphapp.utils

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

fun executeDuglasPeucker(srcPoints: LinkedHashMap<Long, Long>, epsilon: Double): LinkedHashMap<Long, Long> {
    if (srcPoints.size <= 1 || epsilon <= 0) {
        return srcPoints
    }

    val outPoints = LinkedHashMap<Long, Long>()

    val firstX = srcPoints.keys.first()
    val firstY = srcPoints.getValue(firstX)

    val lastX = srcPoints.keys.last()
    val lastY = srcPoints.getValue(lastX)

    outPoints[firstX] = firstY

    reducePoints(
        srcPoints = srcPoints,
        outPoints = outPoints,
        epsilon = epsilon,
        begin = 0,
        end = srcPoints.size - 1
    )

    outPoints[lastX] = lastY

    return outPoints
}

private fun getPointLineDistance(x: Long, y: Long, x1: Long, y1: Long, x2: Long, y2: Long): Double {
    val doubleArea = abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1)
    val lineSegmentLength = sqrt((x2 - x1).toDouble().pow(2) + (y2 - y1).toDouble().pow(2.0))
    return if (lineSegmentLength != 0.0) doubleArea / lineSegmentLength
    else 0.0
}

private fun reducePoints(
    srcPoints: LinkedHashMap<Long, Long>,
    outPoints: LinkedHashMap<Long, Long>,
    epsilon: Double,
    begin: Int,
    end: Int
): LinkedHashMap<Long, Long> {
    if (begin + 1 == end) {
        return srcPoints
    }

    var maxDistance = -1.0
    var maxIndex = 0

    val xPoints = srcPoints.keys.toList()

    for (i in begin + 1 until end) {
        val currentX = xPoints[i]
        val currentY = srcPoints.getValue(currentX)
        val startPointX = xPoints[begin]
        val startPointY = srcPoints.getValue(startPointX)
        val endPointX = xPoints[end]
        val endPointY = srcPoints.getValue(endPointX)

        val distance = getPointLineDistance(
            x = currentX,
            y = currentY,
            x1 = startPointX,
            y1 = startPointY,
            x2 = endPointX,
            y2 = endPointY
        )

        if (distance > maxDistance) {
            maxDistance = distance
            maxIndex = i
        }
    }

    if (maxDistance > epsilon) {
        reducePoints(srcPoints, outPoints, epsilon, begin, maxIndex)

        val maxPointX = xPoints[maxIndex]
        val maxPointY = srcPoints.getValue(maxPointX)
        outPoints[maxPointX] = maxPointY

        reducePoints(srcPoints, outPoints, epsilon, maxIndex, end)
    }

    return srcPoints
}