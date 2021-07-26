package denisbuketa.android.rstone_purchase_card

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_rstone_purchase_card.*
import kotlinx.android.synthetic.main.view_rstone_subscription_view.view.*

class RstonePurchaseCardActivity : AppCompatActivity() {

    private val subscriptionViewHorizontalMargin by lazy { resources.getDimensionPixelSize(R.dimen.subscription_view_horizontal_margin) }
    private val subscriptionViewCornerRadius by lazy { resources.getDimension(R.dimen.subscription_view_corner_radius) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rstone_purchase_card)

        addButton.setOnClickListener { addSubscriptionView(false) }
        addButton2.setOnClickListener { addSubscriptionView(true) }
    }

    private fun addSubscriptionView(shouldShowUnlimitedLanguagesLabel: Boolean) {
        val subscriptionView = SubscriptionView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            languageName.text = "Spanish - Latin America"
            subscriptionDuration.text = "3 Months"
            pricePerMonth.text = "$18.33/mo"
            totalPrice.text = "$54.99 every 3 months"
            this.shouldShowUnlimitedLanguagesLabel = shouldShowUnlimitedLanguagesLabel
            radius = subscriptionViewCornerRadius
            elevation = 0f
            layoutParams = ViewGroup.MarginLayoutParams(layoutParams).apply {
                marginStart = subscriptionViewHorizontalMargin
                marginEnd = subscriptionViewHorizontalMargin
            }
        }
        root.addView(subscriptionView)
    }
}