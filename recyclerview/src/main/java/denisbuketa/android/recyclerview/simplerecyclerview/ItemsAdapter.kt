package denisbuketa.android.recyclerview.simplerecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import denisbuketa.android.recyclerview.R
import kotlinx.android.synthetic.main.view_item.view.*

class ItemsAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = layoutInflater.inflate(R.layout.view_item, parent, false)
        return ItemViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, index: Int) {
        holder.bind(items[index])
    }

    fun setItems(items: List<Item>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Item) {
            view.text.text = item.text
        }
    }
}