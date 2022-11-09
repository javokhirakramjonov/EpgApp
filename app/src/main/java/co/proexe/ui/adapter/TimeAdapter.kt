package co.proexe.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import co.proexe.databinding.ItemTimeBinding
import co.proexe.model.data.DayTile

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private lateinit var list : List<DayTile>
    private var selectedElement = -1

    fun submitList(list: List<DayTile>) {
        this.list = list
        selectedElement = list.indexOfFirst { it.isSelected }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.title.setOnClickListener {
                if(absoluteAdapterPosition == selectedElement)
                    return@setOnClickListener
                list[selectedElement].isSelected = false
                list[absoluteAdapterPosition].isSelected = true
                notifyItemChanged(selectedElement, false)
                selectedElement = absoluteAdapterPosition
                notifyItemChanged(selectedElement, true)
            }
        }

        fun bind() {
            val item = list[absoluteAdapterPosition]
            binding.apply {
                title.text = binding.root.context.getString(item.dayLabel)
                changeColor(title, item.isSelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            changeColor(holder.binding.title, payloads[0] as Boolean)
        }
    }

    private fun changeColor(textView: AppCompatTextView, isSelected: Boolean) {
        if(isSelected) {
            textView.setTextColor(Color.parseColor("#4197CA"))
        } else {
            textView.setTextColor(Color.parseColor("#67676D"))
        }
    }

    override fun getItemCount(): Int {
        if(!::list.isInitialized)
            return 0
        return list.size
    }

}