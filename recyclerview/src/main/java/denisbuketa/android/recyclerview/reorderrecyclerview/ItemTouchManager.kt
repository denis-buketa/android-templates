package denisbuketa.android.recyclerview.reorderrecyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val NONE = 0

/**
 * Manager that allows the user to swipe to dismiss and drag & drop items in RecyclerView.
 */
class ItemTouchManager {

    interface ItemTouchViewHolder {

        fun onDragged()

        fun onCleared()
    }

    interface ItemTouchAdapter {

        fun moveItem(fromIndex: Int, toIndex: Int)
    }

    val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback())
    var itemTouchAdapter: ItemTouchAdapter? = null

    fun startDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    inner class ItemTouchHelperCallback : ItemTouchHelper.Callback() {

        private val dragDirections: Int = ItemTouchHelper.UP or
                ItemTouchHelper.DOWN or
                ItemTouchHelper.START or
                ItemTouchHelper.END
        private val swipeDirections: Int = NONE

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = makeMovementFlags(dragDirections, swipeDirections)

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)

            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                (viewHolder as? ItemTouchViewHolder)?.onDragged()
            }
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromIndex = viewHolder.adapterPosition
            val toIndex = target.adapterPosition
            itemTouchAdapter?.moveItem(fromIndex, toIndex)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Do nothing
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            (viewHolder as? ItemTouchViewHolder)?.onCleared()
        }
    }
}