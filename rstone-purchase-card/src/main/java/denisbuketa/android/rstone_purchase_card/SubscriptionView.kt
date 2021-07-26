package denisbuketa.android.rstone_purchase_card

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.view_rstone_subscription_view.view.*

private const val SHOULD_SHOW_UNLIMITED_LANGUAGES_LABEL_DEFAULT = false

class SubscriptionView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    var shouldShowUnlimitedLanguagesLabel: Boolean = SHOULD_SHOW_UNLIMITED_LANGUAGES_LABEL_DEFAULT
        set(value) {
            field = value
            setHeader()
        }

    init {
        inflate(context, R.layout.view_rstone_subscription_view, this)
        parseAttributes(attrs)
        setBackground()
        setHeader()
        makeMeClickable()
    }

    private fun setBackground() {
        background = ResourcesCompat.getDrawable(resources, R.drawable.subscription_view_background, null)
    }

    private fun makeMeClickable() {
        isClickable = true
        isFocusable = true

        // Set "selectable" foreground
        val selectableBackgroundTypedValue = TypedValue()
        context.theme.resolveAttribute(android.R.attr.selectableItemBackground, selectableBackgroundTypedValue, true)
        foreground = ResourcesCompat.getDrawable(resources, selectableBackgroundTypedValue.resourceId, null)
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.SubscriptionView, 0, 0)
        try {
            shouldShowUnlimitedLanguagesLabel = typedArray.getBoolean(
                R.styleable.SubscriptionView_showUnlimitedLanguagesLabel,
                SHOULD_SHOW_UNLIMITED_LANGUAGES_LABEL_DEFAULT
            )
        } finally {
            typedArray.recycle()
        }
    }

    private fun setHeader() {
        if (shouldShowUnlimitedLanguagesLabel) {
            languageName.visibility = View.GONE
            unlimitedLanguagesLabel.visibility = View.VISIBLE
        } else {
            languageName.visibility = View.VISIBLE
            unlimitedLanguagesLabel.visibility = View.GONE
        }
    }
}