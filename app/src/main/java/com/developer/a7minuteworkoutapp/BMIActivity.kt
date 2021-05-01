package com.developer.a7minuteworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.developer.a7minuteworkoutapp.databinding.ActivityBmiBinding
import java.lang.Exception

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBmiBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarBmiActivity)
        val actionbar = supportActionBar
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"

        }
        binding.toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.rbMetricUnits.setOnClickListener {
            binding.llBmiLayout.visibility = View.VISIBLE
            binding.llBmiLayoutUs.visibility = View.GONE
        }
        binding.rbUsUnits.setOnClickListener {
            binding.llBmiLayout.visibility = View.GONE
            binding.llBmiLayoutUs.visibility = View.VISIBLE
        }

        binding.bmiCalculate.setOnClickListener {
            try {
                val weightInKg = binding.etWeightInKg.text.toString().toDouble()
                val heightInCm = binding.etHeightInCm.text.toString().toDouble()
                val calculateBmi = ((weightInKg / heightInCm) / heightInCm) * 10000.toDouble()
                val calculateBmiNumber = String.format("%.2f", calculateBmi).toDouble()
                binding.yourBmi.text = "YOUR BMI"
                binding.bmiNumber.text = calculateBmiNumber.toString()
                binding.bmiMetric.text = when (calculateBmiNumber) {
                    in 1.01..18.40 -> "UnderWeight"
                    in 18.41..24.99 -> "Normal"
                    in 25.00..29.99 -> "Overweight"
                    in 30.00..70.00 -> "Obese"
                    else -> "Incorrect Value"
                }
                binding.shapeDetail.text = when (calculateBmiNumber) {
                    in 1.01..18.40 -> "You need to gain some weight"
                    in 18.41..24.99 -> "Congratulations, You are in good Shape."
                    in 25.00..29.99 -> "You need to loose some weight"
                    in 30.00..70.00 -> "You need to loose good weight"
                    else -> "Incorrect Value"
                }
                binding.yourBmi.visibility = View.VISIBLE
                binding.bmiNumber.visibility = View.VISIBLE
                binding.bmiMetric.visibility = View.VISIBLE
                binding.shapeDetail.visibility = View.VISIBLE
            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Please enter correct values",
                Toast.LENGTH_SHORT).show()
                binding.yourBmi.visibility = View.GONE
                binding.bmiNumber.visibility = View.GONE
                binding.bmiMetric.visibility = View.GONE
                binding.shapeDetail.visibility = View.GONE
            }
        }
        binding.bmiCalculateUs.setOnClickListener {
            try {
                val weightInPounds = binding.etWeightInPounds.text.toString().toDouble()
                val givenHeightInFeet = binding.etHeightInFeet.text.toString().toDouble()
                val givenHeightInInches = binding.etHeightInInches.text.toString().toDouble()
                val heightInInches = (givenHeightInFeet * 12) + givenHeightInInches
                val calculateBmi = (weightInPounds / heightInInches / heightInInches) * 703.toDouble()
                val calculateBmiNumber = String.format("%.2f", calculateBmi).toDouble()
                binding.yourBmiUs.text = "YOUR BMI"
                binding.bmiNumberUs.text = calculateBmiNumber.toString()
                binding.bmiMetricUs.text = when (calculateBmiNumber) {
                    in 1.01..18.40 -> "UnderWeight"
                    in 18.41..24.99 -> "Normal"
                    in 25.00..29.99 -> "Overweight"
                    in 30.00..70.00 -> "Obese"
                    else -> "Incorrect Value"
                }
                binding.shapeDetailUs.text = when (calculateBmiNumber) {
                    in 1.01..18.40 -> "You need to gain some weight"
                    in 18.41..24.99 -> "Congratulations, You are in good Shape."
                    in 25.00..29.99 -> "You need to loose some weight"
                    in 30.00..70.00 -> "You need to loose good weight"
                    else -> "Incorrect Value"
                }
                binding.yourBmiUs.visibility = View.VISIBLE
                binding.bmiNumberUs.visibility = View.VISIBLE
                binding.bmiMetricUs.visibility = View.VISIBLE
                binding.shapeDetailUs.visibility = View.VISIBLE

            }catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Please enter correct values",
                        Toast.LENGTH_SHORT).show()
                binding.yourBmiUs.visibility = View.GONE
                binding.bmiNumberUs.visibility = View.GONE
                binding.bmiMetricUs.visibility = View.GONE
                binding.shapeDetailUs.visibility = View.GONE
            }
        }
    }
}