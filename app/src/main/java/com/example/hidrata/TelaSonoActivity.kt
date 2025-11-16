package com.example.hidrata

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TelaSonoActivity : AppCompatActivity() {

    private lateinit var adapter: SonoAdapter
    private lateinit var lista: MutableList<Sono>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_sono)

        val input = findViewById<EditText>(R.id.inputSono)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarSono)
        val btnVoltar = findViewById<Button>(R.id.btnVoltarSono)
        val recycler = findViewById<RecyclerView>(R.id.recyclerSono)

        // Carrega lista do SharedPreferences
        lista = carregarLista()

        adapter = SonoAdapter(lista) { pos ->
            lista.removeAt(pos)
            salvarLista()
            adapter.notifyDataSetChanged()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnVoltar.setOnClickListener {
            finish()
        }

        btnSalvar.setOnClickListener {
            val textoHoras = input.text.toString()
            val horas = textoHoras.toDoubleOrNull()

            if (horas == null || horas <= 0) {
                input.error = "Digite um número válido."
                return@setOnClickListener
            }

            val dataHora = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(java.util.Date())

            val registro = Sono(horas, dataHora)
            lista.add(registro)

            salvarLista()
            adapter.notifyDataSetChanged()

            input.setText("")
        }
    }

    private fun salvarLista() {
        val prefs = getSharedPreferences("sono", MODE_PRIVATE)
        val edit = prefs.edit()
        val gson = Gson()
        edit.putString("lista", gson.toJson(lista))
        edit.apply()
    }

    private fun carregarLista(): MutableList<Sono> {
        val prefs = getSharedPreferences("sono", MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("lista", null)

        return if (json == null) {
            mutableListOf()
        } else {
            val type = object : TypeToken<MutableList<Sono>>() {}.type
            gson.fromJson(json, type)
        }
    }
}
