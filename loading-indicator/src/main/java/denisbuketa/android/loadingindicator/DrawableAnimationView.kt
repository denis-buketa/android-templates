package denisbuketa.android.loadingindicator

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout

class DrawableAnimationView : FrameLayout {
    private var animationView: View? = null
    private var drawableResource: Drawable? = null
    private var drawableWidth = 0
    private var drawableHeight = 0
    private var animateOnVisibilityChange = ANIMATE_ON_VISIBILITY_CHANGE

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        extractAttributes(context, attrs)
        animationView = View(context)
        animationView!!.background = drawableResource
        val params = LayoutParams(drawableWidth, drawableHeight, Gravity.CENTER)
        animationView!!.layoutParams = params
        addView(animationView)
        visibility = visibility
    }

    private fun extractAttributes(context: Context, attributeSet: AttributeSet?) {
        if (attributeSet != null) {
            val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DrawableAnimationView)
            drawableResource = typedArray.getDrawable(R.styleable.DrawableAnimationView_animationDrawable)
            drawableWidth = typedArray.getDimensionPixelSize(R.styleable.DrawableAnimationView_animationWidth, LayoutParams.WRAP_CONTENT)
            drawableHeight =
                typedArray.getDimensionPixelSize(R.styleable.DrawableAnimationView_animationHeight, LayoutParams.WRAP_CONTENT)
            animateOnVisibilityChange = typedArray.getBoolean(
                R.styleable.DrawableAnimationView_animateOnVisibilityChange,
                ANIMATE_ON_VISIBILITY_CHANGE
            )
            typedArray.recycle()
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (visibility) {
            View.VISIBLE -> if (animateOnVisibilityChange) {
                startDefaultAnimation()
            }
            View.INVISIBLE, View.GONE -> if (animateOnVisibilityChange) {
                stopDefaultAnimation()
            }
            else -> if (animateOnVisibilityChange) {
                stopDefaultAnimation()
            }
        }
    }

    fun setRepeat(enableRepeat: Boolean) {
        getAnimationView()!!.isOneShot = !enableRepeat
    }

    fun setDrawableResource(drawableResource: Int) {
        animationView!!.setBackgroundResource(drawableResource)
    }

    private fun startDefaultAnimation() {
        if (getAnimationView() != null) {
            getAnimationView()!!.start()
        }
    }

    private fun stopDefaultAnimation() {
        if (getAnimationView() != null) {
            getAnimationView()!!.stop()
        }
    }

    fun startAnimation() {
        val animation = getAnimationView()
        animation!!.setVisible(true, true)
        animation.isOneShot = false
        animation.start()
    }

    private fun getAnimationView(): AnimationDrawable? {
        return animationView!!.background as AnimationDrawable
    }

    fun stopAnimation() {
        val animation = getAnimationView()
        animation!!.isOneShot = true
    }

    companion object {
        private const val ANIMATE_ON_VISIBILITY_CHANGE = true
    }
}