package denisbuketa.android.recyclerview.reorderrecyclerview

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import denisbuketa.android.propertyanimation.PropertyAnimationUtils
import denisbuketa.android.recyclerview.R
import kotlinx.android.synthetic.main.view_item.view.text
import kotlinx.android.synthetic.main.view_reorder_item.view.*

class ItemsAdapter(
    private val layoutInflater: LayoutInflater,
    private val itemTouchHelper: ItemTouchHelper
) :
    RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = layoutInflater.inflate(R.layout.view_reorder_item, parent, false)
        return ItemViewHolder(view, itemTouchHelper)
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

    fun moveItem(fromIndex: Int, toIndex: Int) {
        val item = items[fromIndex]
        items.removeAt(fromIndex)
        items.add(toIndex, item)
    }

    class ItemViewHolder(
        private val view: View,
        private val itemTouchHelper: ItemTouchHelper
    ) : RecyclerView.ViewHolder(view) {

        fun bind(item: Item) {
            view.text.text = item.text
            initDragHandle()
        }

        fun updateBackgroundColor(isSelected: Boolean) {
            val toColorRes = if (isSelected) R.color.colorGray50 else R.color.colorWhite
            PropertyAnimationUtils.animateViewBackgroundColorChange(view, toColorRes)
        }

        private fun initDragHandle() {
            view.reorderHandle.setOnTouchListener { view, motionEvent ->

                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchHelper.startDrag(this)
                }

                return@setOnTouchListener true
            }
        }
    }
}