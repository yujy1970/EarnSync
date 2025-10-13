<template>
  <div ref="chartRef" class="chart-container"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { useMarketStore } from '@/stores/marketStore'

const chartRef = ref(null)
const chart = ref(null)
const marketStore = useMarketStore()

// === 统一日期格式函数 ===
function normalizeDates(arr) {
  if (!Array.isArray(arr)) return []
  return arr
    .map(([d, v]) => {
      let ts
      if (typeof d === 'number') {
        // 秒级时间戳 → 毫秒
        ts = d < 1e11 ? d * 1000 : d
      } else if (typeof d === 'string') {
        ts = Date.parse(d)
      } else {
        ts = +d
      }
      return [ts, v]
    })
    .filter(([ts, v]) => !isNaN(ts) && v != null)
}

// === 主绘图函数 ===
function renderChart(indexData, equityData) {
  if (!chart.value) chart.value = echarts.init(chartRef.value)

  indexData = normalizeDates(indexData)
  equityData = normalizeDates(equityData)

  if (!indexData.length || !equityData.length) {
    console.warn('No data available for chart.')
    return
  }

  // 统一时间范围
  const minDate = Math.min(indexData[0][0], equityData[0][0])
  const maxDate = Math.max(indexData[indexData.length - 1][0], equityData[equityData.length - 1][0])

  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'cross' },
      formatter: (params) => {
        const date = new Date(params[0].value[0]).toISOString().slice(0, 10)
        const lines = params.map((p) => `${p.seriesName}: ${p.value[1]?.toFixed(2) ?? '—'}`)
        return `${date}<br/>${lines.join('<br/>')}`
      },
    },
    legend: {
      data: ['指数', '资金曲线'],
      top: 10,
    },
    grid: { left: 60, right: 60, top: 60, bottom: 60 },
    xAxis: {
      type: 'time',
      min: minDate,
      max: maxDate,
      axisLabel: {
        formatter: (value) => new Date(value).toISOString().slice(0, 10),
      },
    },
    yAxis: [
      {
        type: 'value',
        name: '指数',
        position: 'left',
        scale: true,
        axisLabel: { color: '#1f78b4' },
      },
      {
        type: 'value',
        name: '资金',
        position: 'right',
        scale: true,
        axisLabel: { color: '#33a02c' },
      },
    ],
    series: [
      {
        name: '指数',
        type: 'line',
        data: indexData,
        showSymbol: false,
        smooth: true,
        lineStyle: { width: 1.5, color: '#1f78b4' },
        connectNulls: true,
      },
      {
        name: '资金曲线',
        type: 'line',
        data: equityData,
        yAxisIndex: 1,
        showSymbol: false,
        smooth: true,
        lineStyle: { width: 1.5, color: '#33a02c' },
        connectNulls: true,
      },
    ],
  }

  chart.value.setOption(option)
  window.addEventListener('resize', () => chart.value?.resize())
}

// === 数据监听 ===
onMounted(() => {
  if (marketStore.indexData && marketStore.equityData) {
    renderChart(marketStore.indexData, marketStore.equityData)
  }
})

watch(
  () => [marketStore.indexData, marketStore.equityData],
  ([indexData, equityData]) => {
    if (indexData?.length && equityData?.length) {
      renderChart(indexData, equityData)
    }
  },
  { deep: true },
)
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
  min-height: 480px;
}
</style>
