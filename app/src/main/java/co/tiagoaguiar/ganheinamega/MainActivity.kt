package co.tiagoaguiar.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        val result = prefs.getString("result", null)
        if(result != null){
            txtResult.text= "Ultima aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        val quantidade = text.toInt()
        if (text.isEmpty() || quantidade<6 || quantidade >15) {
            Toast.makeText(this, "Informe um numero entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()
        val random = Random()
        while (true) {
            val number = random.nextInt(60)
            numbers.add(number + 1)

            if (quantidade === numbers.size) {
                break
            }
        }
        txtResult.text = numbers.joinToString(" - ")

        val editor = prefs.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()
    }
}