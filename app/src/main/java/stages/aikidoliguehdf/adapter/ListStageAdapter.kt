package stages.aikidoliguehdf.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import stages.aikidoliguehdf.data.Stages
import stages.aikidoliguehdf.databinding.ListStagesBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class ListStageAdapter: RecyclerView.Adapter<ListStageAdapter.ListStageViewHolder>() {

    var items = ArrayList<Stages>()
    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    fun setDataList(items: List<Stages>) {
        this.items = items.toMutableList() as ArrayList<Stages>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListStageViewHolder {
        val binding = ListStagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListStageViewHolder(binding, mListener)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ListStageViewHolder, position: Int) {
        val item = items[position]
        val niceDate = try {
            val date = LocalDate.parse(item.startdate)
            DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date)
        } catch (_: Exception) {
            item.startdate
        }

        holder.binding.txtTitle.text = item.title
        holder.binding.txtDate.text = niceDate
        holder.binding.txtTime.text = formatTime(item)

        val formattedCost = formatCost(item.cost)
        holder.binding.txtCost.text = formattedCost
        if (formattedCost == "Gratuit") {
            holder.binding.imgEuro.visibility = android.view.View.GONE
        } else {
            holder.binding.imgEuro.visibility = android.view.View.VISIBLE
        }
    }

    private fun formatTime(item: Stages): String {
        val startH = convertHours(item.starthours, item.startampm)
        val startM = convertMinutes(item.startminutes)
        val endH = convertHours(item.endhours, item.endampm)
        val endM = convertMinutes(item.endminutes)

        if (startH.isEmpty() && endH.isEmpty()) {
            return "Horaires à venir"
        }
        val start = if (startH.isNotEmpty()) "${startH}h$startM" else ""
        val end = if (endH.isNotEmpty()) "${endH}h$endM" else ""
        return if (start.isNotEmpty() && end.isNotEmpty()) {
            "$start - $end"
        } else start.ifEmpty { end }
    }

    private fun convertHours(hours: String, ampm: String): String {
        if (hours.isEmpty()) return ""
        val h = hours.toIntOrNull() ?: return hours
        return when {
            ampm == "PM" && h in 1..11 -> (h + 12).toString().padStart(2, '0')
            ampm == "AM" && h == 12 -> "00"
            else -> h.toString().padStart(2, '0')
        }
    }

    private fun convertMinutes(minutes: String): String {
        if (minutes.isEmpty() || minutes == "0" || minutes == "00") return "00"
        return minutes.padStart(2, '0')
    }

    private fun formatCost(cost: String): String {
        val trimmed = cost.trim()
        if (trimmed.isEmpty() || trimmed == "0" || trimmed.lowercase() == "gratuit") {
            return "Gratuit"
        }
        return if (trimmed.contains("€")) trimmed else "$trimmed €"
    }

    class ListStageViewHolder (val binding: ListStagesBinding, listener: OnItemClickListener): RecyclerView.ViewHolder(binding.root) {
        init{
            itemView.setOnClickListener{
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}