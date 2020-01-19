package denisbuketa.android.recyclerview.simplerecyclerview

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import denisbuketa.android.recyclerview.R
import kotlinx.android.synthetic.main.activity_recycler_view_list.*

class RecyclerViewListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_list)

        val layoutManager = LinearLayoutManager(this)
        val itemsAdapter =
            ItemsAdapter(
                LayoutInflater.from(this)
            )

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = itemsAdapter

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
