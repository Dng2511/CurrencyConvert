package com.example.caculatorlinear


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button


class MainActivity : AppCompatActivity() {

    private lateinit var etFirstConversion: android.widget.TextView
    private lateinit var etSecondConversion: android.widget.TextView
    private lateinit var spinnerFirstConversion: Spinner
    private lateinit var spinnerSecondConversion: Spinner
    private var currentInput = "";

    private val exchangeRates = mapOf(
        Pair("USD", "EUR") to 0.85f,
        Pair("USD", "VND") to 23000f,
        Pair("EUR", "USD") to 1.18f,
        Pair("EUR", "VND") to 27000f,
        Pair("VND", "USD") to 0.000043f,
        Pair("VND", "EUR") to 0.000037f
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFirstConversion = findViewById(R.id.textView4)
        etSecondConversion = findViewById(R.id.textView5)
        spinnerFirstConversion = findViewById(R.id.spinner)
        spinnerSecondConversion = findViewById(R.id.spinner2)

        setupSpinners()

    }

    private fun setupSpinners() {
        val currencies = arrayOf("USD", "EUR", "VND", "JPY", "GBP");
        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.buttonc
        )
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }



        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFirstConversion.adapter = adapter
        spinnerSecondConversion.adapter = adapter
        spinnerSecondConversion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateConversionResult()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
    private fun appendNumber(number: String) {
        currentInput += number
        etFirstConversion.text = currentInput
    }


    private fun updateConversionResult() {
        val fromCurrency = spinnerFirstConversion.selectedItem.toString()
        val toCurrency = spinnerSecondConversion.selectedItem.toString()
        val amountText = etFirstConversion.text.toString()

        if (amountText.isNotEmpty()) {
            val amount = amountText.toFloatOrNull()
            if (amount != null) {
                val rate = exchangeRates[Pair(fromCurrency, toCurrency)] ?: 1.0f
                val result = amount * rate

                etSecondConversion.setText(result.toString())
            }
        } else {
            etSecondConversion.setText("")
        }
    }
}