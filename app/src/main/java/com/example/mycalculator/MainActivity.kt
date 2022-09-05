package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var lastDot = false
    var lastNumeric = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        lastDot = false
        lastNumeric = true
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if (lastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
                    value.contains("+") ||
                    value.contains("x") ||
                    value.contains("-")
        }
    }

    fun omEquals(view: View) {
        if (lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("x")){
                    val splitValue = tvValue.split("x")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one  = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length-2)
        return value
    }
    @SuppressLint("SetTextI18n")
    fun onPlusMinus(view: View){
        val digit = tvInput?.text.toString()
        val digit1 = digit.substring(0, digit.length-1)
        if (digit1.toDouble()<0){
            tvInput?.text = digit.substring(1, digit1.length)
        }else if (digit1.toDouble()>0){
            tvInput?.text = "-$digit1"
        }
    }
}