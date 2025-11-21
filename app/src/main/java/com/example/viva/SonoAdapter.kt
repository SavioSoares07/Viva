package com.example.viva

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SonoAdapter(
    private val lista: MutableList<Sono>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<SonoAdapter.ViewHolder>() {

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val txtHoras: TextView = v.findViewById(R.id.txtHorasSono)
        val txtDataHora: TextView = v.findViewById(R.id.txtDataHoraSono)
        val btnExcluir: Button = v.findViewById(R.id.btnExcluirSono)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sono, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.txtHoras.text = "${item.horas} horas"
        holder.txtDataHora.text = item.dataHora

        holder.btnExcluir.setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount() = lista.size
}
