package denisbuketa.android.elevationtopappbarlayout

import android.animation.ArgbEvaluator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import kotlin.math.min

private const val TOP_APP_BAR_INDEX = 0
private const val CHILD_VIEW_INDEX = 1

/**
 * Arbitrary value used to determine how much the user needs to scroll for the top app bar to reach its maximum elevation.
 *
 * The bigger the value, the longer it takes for the top app bar to reach maximum elevation on user's scroll.
 */
private const val DEFAULT_ELEVATION_SCROLL_THROTTLE = 8f

/**
 * This layout changes the elevation of the top app bar (first child) based on the scroll state from the second child.
 * If the second child is not scrolled elevation is 0, if it is, elevation is 4dp
 */
class ElevationTopAppBarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val argbEvaluator: ArgbEvaluator = ArgbEvaluator()

    // Colors to update the top app bar
    @ColorInt
    private val zeroElevationColor: Int
    @ColorInt
    private val maxElevationColor: Int

    @Dimension
    private val topAppBarMaxElevation: Float

    private var elevationScrollThrottle = DEFAULT_ELEVATION_SCROLL_THROTTLE

    private lateinit var topAppBar: View

    init {
        clipToPadding = false
        orientation = VERTICAL

        /**
         * Parse attributes
         */

        val defaultZeroElevationColor = ContextCompat.getColor(context, R.color.colorWhite)
        val defaultMaxElevationColor = ContextCompat.getColor(context, R.color.colorGray)
        val defaultTopAppBarMaxElevation = resources.getDimensionPixelSize(R.dimen.top_app_bar_max_elevation).toFloat()

        if (attrs == null) {
            with(context.obtainStyledAttributes(attrs, R.styleable.ElevationTopAppBarLayout, 0, 0)) {
                zeroElevationColor = getColor(R.styleable.ElevationTopAppBarLayout_zeroElevationColor, defaultZeroElevationColor)
                maxElevationColor = getInt(R.styleable.ElevationTopAppBarLayout_maxElevationColor, defaultMaxElevationColor)
                topAppBarMaxElevation = getFloat(R.styleable.ElevationTopAppBarLayout_maxElevation, defaultTopAppBarMaxElevation)
                elevationScrollThrottle = getFloat(R.styleable.ElevationTopAppBarLayout_elevationScrollThrottle, DEFAULT_ELEVATION_SCROLL_THROTTLE)
            }
        } else {
            zeroElevationColor = defaultZeroElevationColor
            maxElevationColor = defaultMaxElevationColor
            topAppBarMaxElevation = defaultTopAppBarMaxElevation
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) {
            return
        }

        topAppBar = getChildAt(TOP_APP_BAR_INDEX) ?: throw IllegalStateException("First child (top app bar) missing")

        (getChildAt(CHILD_VIEW_INDEX))?.let {
            when (it) {
                is WithScrollState -> it.setListener { scrollY -> scrollChanged(topAppBar, scrollY) }
                else -> throw IllegalStateException("Second child has unsupported view type")
            }

        } ?: throw IllegalStateException("Missing second (scrollable) child")
    }

    private fun scrollChanged(topAppBar: View, scrollY: Int) {

        // Update top app bar color
        if (scrollY <= topAppBar.height) {
            val fraction = scrollY.toFloat() / topAppBar.height
            val currentColor = argbEvaluator.evaluate(fraction, zeroElevationColor, maxElevationColor) as Int
            topAppBar.setBackgroundColor(currentColor)
        } else {
            topAppBar.setBackgroundColor(maxElevationColor)
        }

        // Update top app bar elevation
        topAppBar.elevation = min(topAppBarMaxElevation, scrollY / DEFAULT_ELEVATION_SCROLL_THROTTLE)
    }
}

open class ScrollViewWithScrollState @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr), WithScrollState {

    private var listener: ((Int) -> Unit)? = null

    override fun setListener(listener: (Int) -> Unit) {
        this.listener = listener
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        listener?.invoke(t)
    }

}

interface WithScrollState {
    fun setListener(listener: (Int) -> Unit)
}