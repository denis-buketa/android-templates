package denisbuketa.android.recyclerview.reorderrecyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val NO_SWIPE_DIRECTIONS = 0

object ReorderUtils {

    // Creates ItemTouchHelper - utility class to add swipe to dismiss and drag & drop support to RecyclerView.
    fun createItemTouchHelper(): ItemTouchHelper =
        ItemTouchHelper(
            object :

                ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
                    NO_SWIPE_DIRECTIONS
                ) {

                // Called when the ViewHolder swiped or dragged
                override fun onSelectedChanged(
                    viewHolder: RecyclerView.ViewHolder?,
                    actionState: Int
                ) {
                    super.onSelectedChanged(viewHolder, actionState)

                    // Update selected view holder on drag action
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        val itemViewHolder = viewHolder as? ItemsAdapter.ItemViewHolder
                        itemViewHolder?.updateBackgroundColor(true)
                    }
                }

                // Called when ItemTouchHelper wants to move the dragged item from its old position to the new position.
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val adapter = recyclerView.adapter as ItemsAdapter
                    val fromIndex = viewHolder.adapterPosition
                    val toIndex = target.adapterPosition

                    // Reorder models in the adapter
                    adapter.moveItem(fromIndex, toIndex)

                    // This ensures that the items are moved as you change position of selected item
                    adapter.notifyItemMoved(fromIndex, toIndex)

                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // Do nothing
                }

                // Called by the ItemTouchHelper when the user interaction with an element is over and it also completed its animation.
                override fun clearView(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ) {
                    super.clearView(recyclerView, viewHolder)
                    (viewHolder as ItemsAdapter.ItemViewHolder).updateBackgroundColor(false)
                }

                override fun isLongPressDragEnabled(): Boolean = false
            }
        )
}