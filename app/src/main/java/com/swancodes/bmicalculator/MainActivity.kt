package com.swancodes.bmicalculator

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swancodes.bmicalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var weight: String? = null
    private var height: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.button.setOnClickListener {
            if (inputCheck()) {
                val bmi = weight!!.toDouble() / ((height!!.toDouble() / 100) * (height!!.toDouble() / 100))
                val formattedBMI = String.format("%.2f", bmi).toDouble()
                calculateBMI(formattedBMI)
            }
            it.hideKeyboard()
        }
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun inputCheck(): Boolean {
        weight = binding.weightEditText.text.toString()
        height = binding.heightEditText.text.toString()

        if (weight.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your weight", Toast.LENGTH_SHORT).show()
            return false
        }

        if (height.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your height", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun calculateBMI(bmi: Double) {
        val emptyView = binding.emptyText
        val result = binding.resultTextView
        val resultDescription = binding.resultDescription
        val resultRangeNo1 = binding.resultRangeNumber1
        val resultRangeNo2 = binding.resultRangeNumber2
        val image: ImageView = binding.body

        result.text = bmi.toString()
        resultRangeNo2.text = getString(R.string.normal_range)
        emptyView.visibility = View.GONE

        var resultText = ""
        var resultRange = ""
        var color = 0

        when {
            bmi < 18.50 -> {
                resultText = getString(R.string.under_weight)
                resultRange = getString(R.string.underweight_range)
                color = getColor(R.color.under_weight)
                image.setImageResource(R.drawable.underweight)
                image.setColorFilter(getColor(R.color.under_weight))
            }
            bmi in 18.50..24.99 -> {
                resultText = getString(R.string.healthy)
                resultRange = getString(R.string.healthy_range)
                color = getColor(R.color.healthy)
                image.setImageResource(R.drawable.healthy)
                image.setColorFilter(getColor(R.color.healthy))
            }
            bmi in 25.00..29.99 -> {
                resultText = getString(R.string.over_weight)
                resultRange = getString(R.string.overweight_range)
                color = getColor(R.color.over_weight)
                image.setImageResource(R.drawable.overweight)
                image.setColorFilter(getColor(R.color.over_weight))
            }
            bmi in 30.00..34.99 -> {
                resultText = getString(R.string.obesity)
                resultRange = getString(R.string.obesity_range)
                color = getColor(R.color.obesity)
                image.setImageResource(R.drawable.obesity)
                image.setColorFilter(getColor(R.color.obesity))
            }
            bmi > 35.00 -> {
                resultText = getString(R.string.extremely_obesity)
                resultRange = getString(R.string.extremely_obesity_range)
                color = getColor(R.color.extremely_obesity)
                image.setImageResource(R.drawable.extremely_obesity)
                image.setColorFilter(getColor(R.color.extremely_obesity))
            }
        }
        resultDescription.setTextColor(color)
        resultDescription.text = resultText

        resultRangeNo1.setTextColor(color)
        resultRangeNo1.text = resultRange
    }
}
