package com.pedrogomezdev.imcapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var isMaleSelected: Boolean = true
    private var currentWeight: Int = 70
    private var currentAge: Int = 18
    private var currentHeight: Int = 120

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubtractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnSubtractAge: FloatingActionButton
    private lateinit var btnPlusAge: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate: Button

    // Es como un atributo estático en java. Se puede acceder desde cualquier lado.
    companion object {
        const val IMC_KEY = "IMC_RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()
        initListeners()
        initUI()
    }

    // Inicializa los componentes
    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubtractWeight = findViewById(R.id.btnSubtractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubtractAge = findViewById(R.id.btnSubtractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate = findViewById(R.id.btnCalculate)

    }

    // Inicializa los listeners
    private fun initListeners() {

        // Cuando se pulse en el viewMale o female, cambia el género activo y establece el color
        // de las tarjetas de género.
        viewMale.setOnClickListener {
            if (!isMaleSelected) {
                changeGender()
                setGenderColor()
            }

        }
        viewFemale.setOnClickListener {
            if (isMaleSelected) {
                changeGender()
                setGenderColor()
            }
        }

        rsHeight.addOnChangeListener { _, value, _ ->
            val df = DecimalFormat("#.##")
            val result = df.format(value)
            currentHeight = result.toInt()
            tvHeight.text = "$result cm"
        }

        btnPlusWeight.setOnClickListener {
            currentWeight++
            setWeight()
        }

        btnSubtractWeight.setOnClickListener {
            currentWeight--
            setWeight()
        }

        btnPlusAge.setOnClickListener {
            currentAge++
            setAge()
        }

        btnSubtractAge.setOnClickListener {
            currentAge--
            setAge()
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }
    }

    private fun navigateToResult(result: Double) {
        val intent = Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        startActivity(intent)
    }

    private fun calculateIMC(): Double {
        // peso / altura^2
        val df = DecimalFormat("#.##")

        // Tenemos que pasar la altura a metros
        val imc = currentWeight / (currentHeight.toDouble() / 100 * currentHeight.toDouble() / 100)
        val result = df.format(imc).toDouble()

        return result
    }

    private fun setAge() {
        tvAge.text = currentAge.toString()
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    // Invierte los valores de los géneros
    private fun changeGender() {
        isMaleSelected = !isMaleSelected
    }

    // Establece el valor de
    private fun setGenderColor() {
        // Establece el color de fondo de las tarjetas y le pasamos por parámetro
        // el valor devuelto de la función getBackgroundColor)
        viewMale.setCardBackgroundColor(getBackgroundColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgroundColor(!isMaleSelected))
    }

    private fun getBackgroundColor(isSelectedComponent: Boolean): Int {
        // Si lo que le pasamos está a true, ponemos el color de seleccionado
        // si no le ponemos el estandar.
        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }

        // El color que almacenamos en colorReference no es el color en sí mismo
        // sino que es una referencia, por lo que para devolver el color, debemos usar
        // ContextCompat.getColor
        return ContextCompat.getColor(this, colorReference)
    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }
}