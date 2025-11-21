import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.viva.Agua
import com.example.viva.R


class AguaAdapter(
    private val lista: MutableList<Agua>,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<AguaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val litros = itemView.findViewById<TextView>(R.id.txtLitros)
        val dataHora = itemView.findViewById<TextView>(R.id.txtDataHora)
        val btnExcluir = itemView.findViewById<Button>(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agua, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        holder.litros.text = "${item.litros} L"
        holder.dataHora.text = item.dataHora

        holder.btnExcluir.setOnClickListener {
            onDelete(position)
        }
    }
}
