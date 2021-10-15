package com.test.graphapp.data.repository

import com.test.graphapp.data.mappers.CPUStatisticsMap
import com.test.graphapp.data.source.Api
import io.reactivex.rxjava3.core.Observable
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject

class CPUStatisticsRepository @Inject constructor(
    private val api: Api,
    private val cpuStatisticsMap: CPUStatisticsMap
) {
    private companion object {
        const val BATCH_SIZE = 1000
    }

    fun getCPUStatistics(): Observable<LinkedHashMap<Long, Long>> =
        api.getCPUStatistics()
            .flatMapObservable { response ->
                Observable.create { emitter ->
                    val stream = response.body()?.byteStream() ?: throw IOException("data is empty")
                    val cpuDataMap = linkedMapOf<Long, Long>()
                    val reader = BufferedReader(InputStreamReader(stream))

                    while (reader.ready()) {
                        val line = reader.readLine()

                        cpuStatisticsMap.map(line)?.let { cpuDataPair ->
                            cpuDataMap[cpuDataPair.first] = cpuDataPair.second
                        }

                        if (cpuDataMap.size == BATCH_SIZE) {
                            emitter.onNext(cpuDataMap)
                            cpuDataMap.clear()
                        }
                    }

                    emitter.onNext(cpuDataMap)
                    emitter.onComplete()
                }
            }
}