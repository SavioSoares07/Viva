package com.example.hidrata

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class TelaPassosActivity : AppCompatActivity() {

    private lateinit var adapter: PassosAdapter
    private lateinit var listaPassos: MutableList<PassosData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_passos)

        val input = findViewById<EditText>(R.id.inputKM)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarKm)
        val recycler = findViewById<RecyclerView>(R.id.recyclerPassos)

        // Carrega lista salva
        listaPassos = carregarLista()

        adapter = PassosAdapter(listaPassos) { pos ->
            listaPassos.removeAt(pos)
            adapter.notifyItemRemoved(pos)
            salvarLista()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnSalvar.setOnClickListener {
            val textoKm = input.text.toString()
            val km = textoKm.toDoubleOrNull()

            if (km == null || km <= 0) {
                input.error = "Insira um valor válido."
                return@setOnClickListener
            }

            val dataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date())

            val novo = PassosData(km, dataHora)

            listaPassos.add(0, novo)
            adapter.notifyItemInserted(0)
            salvarLista()

            input.text.clear()
        }

        findViewById<Button>(R.id.btnVoltarKm).setOnClickListener {
            finish()
        }
    }

    // ------------ SALVAR / CARREGAR ------------

    private fun salvarLista() {
        val prefs = getSharedPreferences("passos", MODE_PRIVATE)
        val json = Gson().toJson(listaPassos)
        prefs.edit().putString("listaPassos", json).apply()
    }

    private fun carregarLista(): MutableList<PassosData> {
        val prefs = getSharedPreferences("passos", MODE_PRIVATE)
        val json = prefs.getString("listaPassos", null) ?: return mutableListOf()

        val type = object : TypeToken<MutableList<PassosData>>() {}.type
        return Gson().fromJson(json, type)
    }
}
