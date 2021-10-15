package com.test.graphapp.domain.usecase

import com.test.graphapp.AppConst
import com.test.graphapp.data.repository.CPUStatisticsRepository
import com.test.graphapp.utils.executeDuglasPeucker
import com.test.graphapp.utils.executeMovingMedian
import io.reactivex.rxjava3.core.Observable
import java.lang.Math.pow
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class CPUStatisticsUseCase @Inject constructor(private val repository: CPUStatisticsRepository) {

    fun getCPUStatistics(): Observable<LinkedHashMap<Long, Long>> =
        repository.getCPUStatistics()
            .map { cpuData ->
                executeMovingMedian(cpuData, AppConst.MOVING_MEDIAN_WINDOW) as LinkedHashMap
            }
            .map { cpuData ->
                executeDuglasPeucker(cpuData, AppConst.DUGLAS_PEUCKER_EPSILON)
            }
}