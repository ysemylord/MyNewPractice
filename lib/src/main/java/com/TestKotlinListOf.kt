package com


fun main() {
    val 贷款数 = 20000f
    val 月利率 = 1.8 / 1000f
    val 贷款月利息 = 月利率 * 贷款数 * 1f
    val 贷款期数 = 2 * 12
    val 每月应还本金 = 贷款数 / 贷款期数
    var 总的月利率 = 0f
    println(String.format("%4s - %4s - %4s - %4s", "当前月份", "当月应还", "当月利息", "当月利率"))
    var i = 1

    var 剩余贷款数量 = 贷款数
    while (剩余贷款数量 > 0.1) {
        val 实际贷款月利率 = 贷款月利息 / 剩余贷款数量 * 100f
        println(
            String.format(
                "第%03d月 - %5.0f - %2.2f - %.2f%%",
                i++,
                剩余贷款数量,
                贷款月利息,
                实际贷款月利率
            )
        )
        总的月利率 += 实际贷款月利率.toFloat()
        剩余贷款数量 = (剩余贷款数量 - 每月应还本金).toFloat()
    }
    println("总的月利率 $总的月利率%")
    println("平均月利率 ${总的月利率 / 贷款期数}%")
    println("平均年利率 ${总的月利率 / 贷款期数 * 12}%")
}

class Test {
}