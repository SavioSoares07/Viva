package com.example.viva

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton // <--- Import necessário
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class TelaSonoActivity : AppCompatActivity() {

    private lateinit var adapter: SonoAdapter
    private lateinit var lista: MutableList<Sono>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_sono)

        val input = findViewById<EditText>(R.id.inputSono)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarSono)

        val btnVoltar = findViewById<ImageButton>(R.id.btnVoltarSono)

        val recycler = findViewById<RecyclerView>(R.id.recyclerSono)

        lista = carregarLista()

        adapter = SonoAdapter(lista) { pos ->
            lista.removeAt(pos)
            salvarLista()
            adapter.notifyItemRemoved(pos)
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

            val dataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date())

            val registro = Sono(horas, dataHora)

            lista.add(0, registro)
            adapter.notifyItemInserted(0)

            salvarLista()

            input.text.clear()
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