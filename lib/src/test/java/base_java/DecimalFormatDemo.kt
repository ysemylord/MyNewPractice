package base_java

import org.junit.Test
import java.text.DecimalFormat

/**
 * DecimalFormat 作用
 * 1. 指定小数的表现形式   例如: 每三位加个逗号：999,999
 * 2. 对小数取约数
 *
 * DecimalFormat(String pattern)
 * pattern是模式
 * # 表示阿拉伯数字
 * 0 表示阿拉伯数字，原来的数位数不足时，用0补位
 *
 * RoundMode 舍入模式 https://www.apiref.com/java11-zh/java.base/java/math/RoundingMode.html
 *
 *UP(BigDecimal.ROUND_UP),//向上舍入
 *DOWN(BigDecimal.ROUND_DOWN),  //向下舍入
 *CEILING(BigDecimal.ROUND_CEILING),
 *FLOOR(BigDecimal.ROUND_FLOOR),
 *HALF_UP(BigDecimal.ROUND_HALF_UP),//四舍五入
 *HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),
 *HALF_EVEN(BigDecimal.ROUND_HALF_EVEN),//四舍六入  五时，偶邻居舍入
 *UNNECESSARY(BigDecimal.ROUND_UNNECESSARY);
 *
 *
 *
 *
 *
 *
 */
class DecimalFormatDemo {
    @Test
    fun testOne() {

        //整数位最少1位，小数位保留4位，不足4位则不显示
        val decimalFormat1 = DecimalFormat("0.####")

        //整数位最少3位，不足用0补位；小数位保留4位，不足则用0补位
        val decimalFormat2 = DecimalFormat("000.0000")

        println(decimalFormat1.format(31.12))//3.12
        println(decimalFormat2.format(31.12))//031.1200
    }

    @Test
    fun testRoundMode() {
        //RoundMode默认为HALF_EVEN
        val decimalFormat1 = DecimalFormat("0.#")

        // 第2个小数位5，第一个位小数位为奇数，则舍去
        //第一个位小数位为偶数，则舍入
        println(decimalFormat1.format(31.15))//31.1
        println(decimalFormat1.format(31.25))//31.2
    }
}