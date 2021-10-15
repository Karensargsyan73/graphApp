package com.test.graphapp.presentation

import androidx.lifecycle.ViewModel
import com.test.graphapp.domain.usecase.CPUStatisticsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@HiltViewModel
class MainViewModel @Inject constructor(
    cpuStatisticsUseCase: CPUStatisticsUseCase
) : ViewModel() {

    var setCpuData = MutableSharedFlow<Map<Long, Long>>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val disposables = CompositeDisposable()
    private val cpuData = mutableMapOf<Long, Long>()

    init {
        disposables.add(
            cpuStatisticsUseCase
                .getCPUStatistics()
                .subscribe(
                    { cpuDataList ->
                        cpuData.putAll(cpuDataList)
                        setCpuData.tryEmit(cpuData)
                    },
                    { th ->
                        th.printStackTrace()
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}