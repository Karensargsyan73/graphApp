package com.test.graphapp

import com.test.graphapp.utils.executeDuglasPeucker
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val srcPoints = linkedMapOf(1L to 5L, 2L to 3L, 5L to 1L, 6L to 4L, 9L to 6L, 11L to 4, 13L to 3L, 14L to 2L, 18L to 5L)


        val outPoints = executeDuglasPeucker(srcPoints, 8.0)

        assertEquals(4, 2 + 2)
    }


//    fun duglasPeucker(
//        srcPoints: LinkedHashMap<Long, Long>,
//        outPoints: LinkedHashMap<Long, Long>,
//        epsilon: Double
//    ): Map<Long, Long> {
//        if (epsilon <= 0)
//            return srcPoints
//        return simplifyPoints(srcPoints, outPoints, epsilon, 0, srcPoints.size - 1)
//    }
//
//    fun getPointLineDistance(x: Long, y: Long, x1: Long, y1: Long, x2: Long, y2: Long): Double {
//        val doubleArea = abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1)
//        val lineSegmentLength = sqrt((x2 - x1).toDouble().pow(2) + (y2 - y1).toDouble().pow(2.0))
//        return if (lineSegmentLength != 0.0) doubleArea / lineSegmentLength
//        else 0.0
//    }
//
//    fun simplifyPoints(
//        srcPoints: LinkedHashMap<Long, Long>,
//        outPoints: LinkedHashMap<Long, Long>,
//        epsilon: Double,
//        begin: Int,
//        end: Int
//    ): Map<Long, Long> {
//        if (begin + 1 == end)
//            return srcPoints;
//
//        var maxDistance = -1.0
//        var maxIndex = 0
//
//        for (i in begin + 1 until end) {
//            val currentX = srcPoints.keys.toList().get(i)
//            val startPoint = srcPoints.keys.toList().get(begin)
//            val endPoint = srcPoints.keys.toList().get(end)
//
//            val distance = getPointLineDistance(
//                x = currentX,
//                y = srcPoints[currentX]!!,
//                x1 = startPoint,
//                y1 = srcPoints[startPoint]!!,
//                x2 = endPoint,
//                y2 = srcPoints[endPoint]!!
//            )
//
//            if (distance > maxDistance) {
//                maxDistance = distance
//                maxIndex = i
//            }
//        }
//
//        if (maxDistance > epsilon) {
//            simplifyPoints(srcPoints, outPoints, epsilon, begin, maxIndex)
//            val x = srcPoints.keys.toList().get(maxIndex)
//            val y = srcPoints[x]!!
//            outPoints[x] = y
//
//            simplifyPoints(srcPoints, outPoints, epsilon, maxIndex, end)
//        }
//
//
//        return srcPoints
//    }
}