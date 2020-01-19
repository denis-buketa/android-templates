package denisbuketa.android.propertyanimation

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

object PropertyAnimationUtils {

    fun animateViewBackgroundColorChange(
        view: View,
        @ColorRes colorToRes: Int
    ) {
        val viewBackground = view.background as? ColorDrawable
            ?: throw PropertyAnimationException("View doesn't have a background set")

        val colorFrom = viewBackground.color
        val colorTo = ResourcesCompat.getColor(view.resources, colorToRes, null)

        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimation.duration = 250 // milliseconds

        colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.start()
    }

    class PropertyAnimationException(message: String) : Throwable(message)
}