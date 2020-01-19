package denisbuketa.android.recyclerview.reorderrecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import denisbuketa.android.recyclerview.R
import kotlinx.android.synthetic.main.activity_recycler_view_list.*

class ReorderRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_list)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        // Init Item Touch Helper
        val itemTouchHelper = ReorderUtils.createItemTouchHelper()

        // Init Layout Manager
        val layoutManager = LinearLayoutManager(this)

        // Init Adapter
        val itemsAdapter = ItemsAdapter(LayoutInflater.from(this), itemTouchHelper)

        // Init RecyclerView
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = itemsAdapter
        itemTouchHelper.attachToRecyclerView(recyclerView)

        itemsAdapter.setItems(provideItems())
    }

    private fun provideItems(): List<Item> {
        return listOf(
            Item("One"),
            Item("Two"),
            Item("Three"),
            Item("Four"),
            Item("Five"),
            Item("Six"),
            Item("Seven"),
            Item("Eight"),
            Item("Nine"),
            Item("Ten")
        )
    }


}
