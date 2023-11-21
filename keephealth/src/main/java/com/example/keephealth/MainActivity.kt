package com.example.keephealth

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.keephealth.databinding.ActivityMainBinding
import java.time.LocalDate
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var baseDailyCoast = calcDailyCoast(binding)
        binding.baseCostDaily.text = baseDailyCoast.toString()
        var dailyCoast = calcDailyCoast(baseDailyCoast)
        binding.costDaily.text = dailyCoast.toString()

        binding.baseCostDaily.addTextChangedListener(
            beforeTextChanged = { text, start, count, after ->
                // 在文本变化之前执行的操作
            },
            onTextChanged = { text, start, before, count ->
                // 在文本变化时执行的操作
            },
            afterTextChanged = { text ->
                // 在文本变化之后执行的操作
                baseDailyCoast = calcDailyCoast(binding)
                binding.baseCostDaily.text = baseDailyCoast.toString()
                dailyCoast = calcDailyCoast(baseDailyCoast)
                binding.costDaily.text = dailyCoast.toString()
            }
        )


        var foodInput = 0f
        binding.foodInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    val foodInputString = binding.foodInput.text.toString()
                    val foodInputKJ = foodInputString.run {
                        var sum = 0f
                        this.split(",").forEach {
                            sum += it.toFloat()
                        }
                        sum
                    }
                    val foodInputKal = foodInputKJ / 4.18
                    binding.foodInputCalc.text = "$foodInputKal kcal 即 ${foodInputKJ} kJ"
                    val canEat = dailyCoast - foodInputKal - binding.goalLossKcal.text.toString()
                        .toFloat()
                    binding.canEat.text =
                        "剩余热量值 ${
                            canEat
                        }kcal 即${canEat * 4.18}kJ"


                    thread {
                        val today: LocalDate = LocalDate.now()

                        val year: Int = today.year
                        val month: Int = today.monthValue
                        val day: Int = today.dayOfMonth
                        val todayString = "$year-$month-$day"

                        val dailyKcal = DailyKcal(
                            todayString,
                            binding.foodInput.text.toString()
                        )
                        AppDataBase.getInstance(this@MainActivity).dailyKcalDao.get(todayString)
                            ?.let {
                                AppDataBase.getInstance(this@MainActivity).dailyKcalDao.update(
                                    dailyKcal
                                )
                            } ?: let {
                            AppDataBase.getInstance(this@MainActivity).dailyKcalDao.insert(dailyKcal)
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })

        // 在适当的位置获取今天的日期，使用 System 默认的时钟
        // 在适当的位置获取今天的日期
        val today: LocalDate = LocalDate.now()

        val year: Int = today.year
        val month: Int = today.monthValue
        val day: Int = today.dayOfMonth

        val todayString = "$year-$month-$day"

        thread {
            AppDataBase.getInstance(this).dailyKcalDao.get(todayString)?.let {
                binding.foodInput.setText(it.kcalInfo)
            }
        }

    }

    private fun calcDailyCoast(baseDailyCoast: Double) = baseDailyCoast * 1.2

    private fun calcDailyCoast(binding: ActivityMainBinding) =
        10 * binding.weight.text.toString().toFloat() + 6.25 * binding.height.text.toString()
            .toFloat() - 5 * binding.age.text.toString().toFloat() + 5

}