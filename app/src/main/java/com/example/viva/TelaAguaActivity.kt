package com.example.viva

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import AguaAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TelaAguaActivity : AppCompatActivity() {

    private lateinit var adapter: AguaAdapter
    private lateinit var listaAgua: MutableList<Agua>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_agua)

        val input = findViewById<EditText>(R.id.inputLitros)
        val btn = findViewById<Button>(R.id.btnSalvar)
        val recycler = findViewById<RecyclerView>(R.id.recyclerAgua)

        listaAgua = carregarLista()

        adapter = AguaAdapter(listaAgua) { pos ->
            listaAgua.removeAt(pos)
            adapter.notifyItemRemoved(pos)
            salvarLista()
        }

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btn.setOnClickListener {
            val litros = input.text.toString().toDoubleOrNull()

            if (litros == null || litros <= 0) {
                input.error = "Insira um valor válido."
                return@setOnClickListener
            }

            val dataHora = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(Date())

            val novo = Agua(litros, dataHora)

            listaAgua.add(0, novo)
            adapter.notifyItemInserted(0)
            salvarLista()

            input.text.clear()
        }

        findViewById<Button>(R.id.btnVoltarAgua).setOnClickListener {
            finish()
        }
    }


    private fun salvarLista() {
        val prefs = getSharedPreferences("agua", MODE_PRIVATE)
        val json = Gson().toJson(listaAgua)
        prefs.edit().putString("lista", json).apply()
    }

    private fun carregarLista(): MutableList<Agua> {
        val prefs = getSharedPreferences("agua", MODE_PRIVATE)
        val json = prefs.getString("lista", null) ?: return mutableListOf()

        val type = object : TypeToken<MutableList<Agua>>() {}.type
        return Gson().fromJson(json, type)
    }
}
