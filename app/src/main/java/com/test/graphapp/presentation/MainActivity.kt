package com.test.graphapp.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.test.graphapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pie = AnyChart.line()
        binding.chartView.setChart(pie)

        lifecycleScope.launchWhenStarted {
            viewModel
                .setCpuData
                .onEach { cpuDataList ->
                    val data: MutableList<DataEntry> = ArrayList()

                    cpuDataList.forEach { cpuData ->
                        data.add(ValueDataEntry(cpuData.key, cpuData.value))
                    }

                    pie.data(data)
                }
                .collect()
        }
    }
}
