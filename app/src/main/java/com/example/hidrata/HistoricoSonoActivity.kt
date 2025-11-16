package com.example.hidrata

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HistoricoSonoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SonoAdapter
    private lateinit var listaSono: MutableList<Sono>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_sono)

        recyclerView = findViewById(R.id.recyclerSono)
        val btnAdicionarSono = findViewById<Button>(R.id.btnAdicionarSono)

        listaSono = carregarSono()
        adapter = SonoAdapter(listaSono) { index ->
            listaSono.removeAt(index)
            salvarSono()
            adapter.notifyDataSetChanged()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdicionarSono.setOnClickListener {
            val intent = Intent(this, TelaSonoActivity::class.java)
            startActivityForResult(intent, 200)
        }
    }

    // RECEBE O RESULTADO DA TELA DE REGISTRO
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 200 && resultCode == RESULT_OK) {

            val horas = data?.getDoubleExtra("sono", 0.0) ?: return

            val agora = java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                .format(java.util.Date())

            val registro = Sono(horas, agora)

            listaSono.add(registro)
            salvarSono()
            adapter.notifyDataSetChanged()
        }
    }

    private fun salvarSono() {
        val prefs = getSharedPreferences("dados", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val gson = Gson()
        val json = gson.toJson(listaSono)

        editor.putString("sono_lista", json)
        editor.apply()
    }

    private fun carregarSono(): MutableList<Sono> {
        val prefs = getSharedPreferences("dados", Context.MODE_PRIVATE)
        val json = prefs.getString("sono_lista", null) ?: return mutableListOf()

        val gson = Gson()
        val tipo = object : TypeToken<MutableList<Sono>>() {}.type

        return gson.fromJson(json, tipo)
    }
}
