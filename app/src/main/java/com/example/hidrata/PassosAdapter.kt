package com.example.hidrata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PassosAdapter(
    private val lista: MutableList<PassosData>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PassosAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val km = itemView.findViewById<TextView>(R.id.txtKm)
        val dataHora = itemView.findViewById<TextView>(R.id.txtDataHoraKm)
        val btnExcluir = itemView.findViewById<Button>(R.id.btnExcluirKm)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_passos, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.km.text = "${item.km} km"
        holder.dataHora.text = item.dataHora

        holder.btnExcluir.setOnClickListener {
            onDelete(position)
        }
    }
}
