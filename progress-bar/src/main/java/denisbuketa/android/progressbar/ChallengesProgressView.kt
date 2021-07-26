package denisbuketa.android.progressbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

class ChallengesProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val stepPaths: MutableList<Path> = mutableListOf()
    private val spaceBetweenAdjacentStepsWidthPx by lazy { resources.getDimension(R.dimen.challenge_progress_step_inner_margin) }

    private val paintCorrect = initStepPaint(R.color.challenge_correct_color)
    private val paintIncorrect = initStepPaint(R.color.challenge_incorrect_color)
    private val paintNoInfo = initStepPaint(R.color.challenge_no_info_color)
    private val paintEmpty = initStepPaint(R.color.challenge_empty_color)

    private var challengesCount: Int = 3

    private var canvasClipPath: Path? = null

//    fun init(challengesCount: Int) {
//        this.challengesCount = 0
//        invalidate()
//    }

    private fun initStepPaint(@ColorRes colorRes: Int): Paint {
        val paint = Paint()
        paint.color = ContextCompat.getColor(context, colorRes)
        paint.style = Paint.Style.FILL

        return paint
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        if (changed) {
            initCanvasClipPath()
            initStepPaths()
        }
    }

    private fun initCanvasClipPath() {
        val diameter = height

        val clipRectFLeft = RectF(
            0f,
            0f,
            0f + diameter,
            height.toFloat()
        )
        val clipRectFRight = RectF(
            width.toFloat() - diameter,
            0f,
            width.toFloat(),
            height.toFloat()
        )
        val middleRectF = RectF(
            0f + diameter / 2,
            0f,
            width.toFloat() - diameter / 2,
            height.toFloat()
        )

        val clipPathStartAngle = 90f
        val clipPathArcSweepAngle = 180f
        canvasClipPath = Path().apply {
            addArc(clipRectFLeft, clipPathStartAngle, clipPathArcSweepAngle)
            addArc(clipRectFRight, clipPathStartAngle, -clipPathArcSweepAngle)
            addRect(middleRectF, Path.Direction.CCW)
        }
    }

    private fun initStepPaths() {

        stepPaths.clear()

        if (challengesCount <= 0) {
            return
        }

        val stepWidth = (width - (challengesCount - 1) * spaceBetweenAdjacentStepsWidthPx) / challengesCount
        for (i in 0 until challengesCount) {
            val stepLeftBound = i * (stepWidth + spaceBetweenAdjacentStepsWidthPx)
            val rectF = RectF(stepLeftBound, 0f, stepLeftBound + stepWidth, height.toFloat())
            val path = Path().apply { addRect(rectF, Path.Direction.CCW) }
            stepPaths.add(path)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvasClipPath?.let { canvas.clipPath(it) }
        stepPaths.forEach { canvas.drawPath(it, paintEmpty) }
    }
}