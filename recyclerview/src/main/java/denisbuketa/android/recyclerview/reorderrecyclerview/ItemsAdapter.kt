package denisbuketa.android.recyclerview.reorderrecyclerview

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import denisbuketa.android.propertyanimation.PropertyAnimationUtils
import denisbuketa.android.recyclerview.R
import kotlinx.android.synthetic.main.view_item.view.text
import kotlinx.android.synthetic.main.view_reorder_item.view.*

class ItemsAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>(), ItemTouchManager.ItemTouchAdapter {

    var itemTouchManager: ItemTouchManager? = null

    private val items: MutableList<Item> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = layoutInflater.inflate(R.layout.view_reorder_item, parent, false)
        return ItemViewHolder(view)
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

    override fun moveItem(fromIndex: Int, toIndex: Int) {
        val item = items[fromIndex]
        items.removeAt(fromIndex)
        items.add(toIndex, item)
        notifyItemMoved(fromIndex, toIndex)
    }

    inner class ItemViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view),
        ItemTouchManager.ItemTouchViewHolder {

        fun bind(item: Item) {
            view.text.text = item.text
            initDragHandle()
        }

        private fun initDragHandle() {
            view.reorderHandle.setOnTouchListener { _, motionEvent ->

                if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                    itemTouchManager?.startDrag(this)
                }

                return@setOnTouchListener true
            }
        }

        override fun onDragged() {
            updateBackgroundColor(true)
        }

        override fun onCleared() {
            updateBackgroundColor(false)
        }

        private fun updateBackgroundColor(isDragged: Boolean) {
            val toColorRes = if (isDragged) R.color.colorGray50 else R.color.colorWhite
            PropertyAnimationUtils.animateViewBackgroundColorChange(view, toColorRes)
        }
    }
}