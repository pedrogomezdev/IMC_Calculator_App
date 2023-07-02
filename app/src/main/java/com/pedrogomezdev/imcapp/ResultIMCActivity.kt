package com.pedrogomezdev.imcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.pedrogomezdev.imcapp.MainActivity.Companion.IMC_KEY

class ResultIMCActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnRecalculate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_imcactivity)

        val result = intent.extras?.getDouble(IMC_KEY) ?: -1.0
        initComponents()
        initListeners()
        initUI(result)
    }


    private fun initComponents(){
        tvResult = findViewById(R.id.tvResult)
        tvIMC = findViewById(R.id.tvIMC)
        tvDescription = findViewById(R.id.tvDescription)
        btnRecalculate = findViewById(R.id.btnRecalculate)
    }

    private fun initListeners(){
        // Con onBackPressed volvemos para atrás
        btnRecalculate.setOnClickListener{ onBackPressed() }
    }

    private fun initUI(result: Double){
        tvIMC.text = result.toString()

        when(result){
            // Bajo peso
            in 0.00..18.50 -> {
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.low_weight))
                tvResult.text = getString(R.string.title_low_weight)
                tvDescription.text = getString(R.string.description_low_weigh)
            }

            // Peso normal
            in 18.51..24.99 -> {
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.normal_weight))
                tvResult.text = getString(R.string.title_normal_weight)
                tvDescription.text = getString(R.string.description_normal_weight)
            }

            // Sobrepeso
            in 25.00..29.99 -> {
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.overweight))
                tvResult.text = getString(R.string.title_overweight)
                tvDescription.text = getString(R.string.description_overweight)
            }

            // Obesidad
            in 30.00..99.99 -> {
                tvResult.setTextColor(ContextCompat.getColor(this,R.color.obesity))
                tvResult.text = getString(R.string.title_obesity)
                tvDescription.text = getString(R.string.description_obesity)
            }

            // Si no es ninguno de los anteriores, error
            else ->     {
                tvIMC.text = getString(R.string.error)
                tvResult.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
            }
        }
    }

}