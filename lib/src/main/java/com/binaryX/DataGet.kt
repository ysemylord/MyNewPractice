package com.binaryX

import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

fun main() {

    val client = OkHttpClient()

    val request: Request = Request.Builder()
        .get()
        .url("https://market.binaryx.pro/getSales?page=1&page_size=200&status=selling&name=&sort=strength&direction=asc&career=0x22F3E436dF132791140571FC985Eb17Ab1846494&value_attr=strength,physique&start_value=86,61&end_value=0,0&pay_addr=")
        .build()

    val call = client.newCall(request)

    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // ...
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            if (response != null && response.isSuccessful) {

                val res = response.body?.string()
                val binaryBean = Gson().fromJson<BinaryBean>(res, BinaryBean::class.java)
                val heroes = binaryBean.data.result.items
                heroes.forEach() { hero ->
                    val heroBnxPrice = hero.price.toDouble() / 1000000000000000000
                    val bnxToGold = 26100//26100当日bnx和gold的转化
                    val heroGoldPrice = heroBnxPrice * bnxToGold
                    val costsOfHero = heroGoldPrice + when (hero.level) {
                        1 -> 220000
                        2 -> 200000
                        3 -> 150000
                        4 -> 0
                        else -> 0
                    }
                    val heroDailyGetInOneBlock = 0.01 + (hero.strength - 85) * 0.005
                    val heroDailyGetInAllBlock =
                        (432000 / 15) * heroDailyGetInOneBlock * 8
                    val day = costsOfHero / heroDailyGetInAllBlock
                    println(
                        "cost :${
                            String.format(
                                "%5.2f",
                                heroBnxPrice
                            )
                        } bnx | name: ${String.format("%s", hero.name)} | level:${hero.level} | back_day:${day.toInt()}"
                    )
                }
            }
        }
    })

}

fun main1() {

    val client = OkHttpClient()

    val request: Request = Request.Builder()
        .get()
        .url("https://market.binaryx.pro/getSales?page=1&page_size=200&status=selling&name=&sort=strength&direction=asc&career=0x22F3E436dF132791140571FC985Eb17Ab1846494&value_attr=strength,physique&start_value=86,61&end_value=0,0&pay_addr=")
        .build()

    val call = client.newCall(request)

    call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            // ...
        }

        @Throws(IOException::class)
        override fun onResponse(call: Call, response: Response) {
            if (response != null && response.isSuccessful) {

                val res = response.body?.string()
                val binaryBean = Gson().fromJson<BinaryBean>(res, BinaryBean::class.java)
                val heroes = binaryBean.data.result.items
                heroes.forEach() { hero ->
                    val heroBnxPrice = hero.price.toDouble() / 1000000000000000000
                    val BnxToGold = 26100//26100当日bnx和gold的转化
                    val heroGoldPrice = heroBnxPrice * BnxToGold
                    val costsOfHero = heroGoldPrice
                    val heroDailyGetInOneBlock = 0.01 + (hero.strength - 85) * 0.005
                    val heroDailyGetInAllBlock =
                        432000 * heroDailyGetInOneBlock / 15 * when (hero.level) {
                            1 -> 2
                            2 -> 4
                            3 -> 8
                            4 -> 16
                            else -> 0
                        }
                    val day = costsOfHero / heroDailyGetInAllBlock
                    println(
                        "cost :${
                            String.format(
                                "%5.2f",
                                heroBnxPrice
                            )
                        } bnx |  level:${hero.level} | back_day:${day.toInt()}"
                    )
                }
            }
        }
    })

}
//以回本周期为最终标准
//成本

