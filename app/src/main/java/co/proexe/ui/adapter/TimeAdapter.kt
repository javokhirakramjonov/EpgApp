package co.proexe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.proexe.databinding.ItemTimeBinding
import co.proexe.model.data.DayTile
import co.proexe.utils.changeColorBySelection

class TimeAdapter : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

    private lateinit var list: List<DayTile>
    private var selectedElement = -1
    private var itemSelector: ((DayTile) -> Unit)? = null

    fun setSelector(selector: (DayTile) -> Unit) {
        itemSelector = selector
    }

    fun submitList(list: List<DayTile>) {
        this.list = list
        selectedElement = list.indexOfFirst { it.isSelected }
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.title.setOnClickListener {
                if (absoluteAdapterPosition == selectedElement)
                    return@setOnClickListener
                list[selectedElement].isSelected = false
                list[absoluteAdapterPosition].isSelected = true
                notifyItemChanged(selectedElement, false)
                selectedElement = absoluteAdapterPosition
                notifyItemChanged(selectedElement, true)
                itemSelector?.invoke(list[absoluteAdapterPosition])
            }
        }

        fun bind() {
            val item = list[absoluteAdapterPosition]
            binding.apply {
                title.text = binding.root.context.getString(item.dayLabel)
                title.changeColorBySelection(root.context, item.isSelected)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.binding.title.changeColorBySelection(
                holder.binding.root.context,
                payloads[0] as Boolean
            )
        }
    }

    override fun getItemCount(): Int {
        if (!::list.isInitialized)
            return 0
        return list.size
    }

}