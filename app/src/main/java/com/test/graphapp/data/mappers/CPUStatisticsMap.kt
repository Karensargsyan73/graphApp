package com.test.graphapp.data.mappers

import javax.inject.Inject

class CPUStatisticsMap @Inject constructor() {
    private val timeRegex = "\\{_time: ([+-]?([0-9]*[.])?[0-9]+),".toRegex()
    private val cpuRegex = ", CPU: ([+-]?([0-9]*[.])?[0-9]+),".toRegex()

    fun map(rawCPUData: String): Pair<Long, Long>? {
        val timeMatch = timeRegex.find(rawCPUData)!!
        val cpuMatch = cpuRegex.find(rawCPUData)!!

        val time = timeMatch.groupValues.lastOrNull()?.replace(".", "")?.toLongOrNull()
        val cpu = cpuMatch.groupValues.lastOrNull()?.replace(".", "")?.toLongOrNull()

        if (time == null || cpu == null) {
            return null
        }

        return time to cpu
    }
}
