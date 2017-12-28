package de.jw.circularrevealsharedelementexample.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.jw.circularrevealsharedelementexample.R
import de.jw.circularrevealsharedelementexample.ui.RecyclerViewClickListener
import de.jw.circularrevealsharedelementexample.ui.RoundedColorView

/**
 * Created by jossi on 27.12.2017.
 */
class SimpleAdapter(private val clickListener: RecyclerViewClickListener) : RecyclerView.Adapter<SimpleAdapter.ViewHolder>() {
    private val items: MutableList<String> = mutableListOf()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_row_main, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = items.size

    fun add(item: String) {
        items.add(item)
        notifyItemInserted(items.size)
    }

    fun addAll(items: List<String>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textView by lazy {
            itemView.findViewById<TextView>(R.id.text_content)
        }
        private val roundedColorView by lazy {
            itemView.findViewById<RoundedColorView>(R.id.roundedColorView)
        }

        fun bind(data: String) {
            textView.text = data
            itemView.setOnClickListener {
                clickListener.onClickRecyclerViewItem(roundedColorView)
            }
        }

    }

}