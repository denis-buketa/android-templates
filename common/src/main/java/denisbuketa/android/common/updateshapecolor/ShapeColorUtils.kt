package denisbuketa.android.common.updateshapecolor

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Utils for updating the color of shape drawable
 */
class ShapeColorUtils {

    companion object {

        /**
         * Taken from here:
         * - https://stackoverflow.com/questions/17823451/set-android-shape-color-programmatically
         */
        fun setColor(context: Context, @ColorRes colorRes: Int, drawable: Drawable) {
            when (drawable) {
                is ShapeDrawable -> drawable.paint.color = ContextCompat.getColor(context, colorRes)
                is GradientDrawable -> drawable.setColor(ContextCompat.getColor(context, colorRes))
                is ColorDrawable ->
                    // alpha value may need to be set again after this call
                    drawable.color = ContextCompat.getColor(context, colorRes)
            }
        }
    }
}