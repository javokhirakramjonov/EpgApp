package co.proexe.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.proexe.databinding.ItemChannelBinding
import co.proexe.model.data.TvProgramme
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class ChannelAdapter : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    private lateinit var list: List<TvProgramme>
    private var itemSelector : ((TvProgramme) -> Unit)? = null

    fun setSelector(selector: (TvProgramme) -> Unit) {
        itemSelector = selector
    }

    fun submitList(list: List<TvProgramme>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                itemSelector?.invoke(list[absoluteAdapterPosition])
            }
        }

        fun bind() {
            val item = list[absoluteAdapterPosition]
            binding.apply {
                title.text = item.title
                description.text =
                    "${formatTime(item.startTime)}-${formatTime(item.endTime)} | ${item.type}"
                Glide.with(binding.root)
                    .load(item.imageUrl)
                    .into(image)
                progress.progress = item.progressPercent
            }
        }
    }

    private fun formatTime(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("hh:mm")
        return simpleDateFormat.format(date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemChannelBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        if (!::list.isInitialized)
            return 0
        return list.size
    }

}