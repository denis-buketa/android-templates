package denisbuketa.android.smoothcorners;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class SmoothCornersView extends AppCompatImageView {

    private float mCenterX = 0;
    private float mCenterY = 0;

    private Path canvasClipPath;

    public SmoothCornersView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w * 1.0f / 2;
        mCenterY = h * 1.0f / 2;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            initCanvasClipPath();
        }
    }

    private void initCanvasClipPath() {
//        Log.d("debug_log", "initCanvasClipPath: height: " + getHeight() + ", width: " + getWidth());
//        final int diameter = getHeight();
//
//        final RectF clipRectFLeft = new RectF(
//                0f,
//                0f,
//                0f + diameter,
//                getHeight()
//        );
//        final RectF clipRectFRight = new RectF(
//                getWidth() - diameter,
//                0f,
//                getWidth(),
//                getHeight()
//        );
//        final RectF middleRectF = new RectF(
//                0f + diameter / 2f,
//                0f,
//                getWidth() - diameter / 2f,
//                getHeight()
//        );
//
//        float clipPathStartAngle = 90f;
//        float clipPathArcSweepAngle = 180f;
//        canvasClipPath = new Path();
//        canvasClipPath.addArc(clipRectFLeft, clipPathStartAngle, clipPathArcSweepAngle);
//        canvasClipPath.addArc(clipRectFRight, clipPathStartAngle, -clipPathArcSweepAngle);
//        canvasClipPath.addRect(middleRectF, Path.Direction.CCW);
        canvasClipPath = SketchRealSmoothRect(0, 0, getWidth(), getHeight(), 200, 200, true, true, true, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (canvasClipPath != null) {
            Log.d("debug_log", "clip path");
            canvas.clipPath(canvasClipPath);
        }

        super.onDraw(canvas);
    }

    public Path SketchRealSmoothRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean bl, boolean br
    ) {

        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        float posX = mCenterX - width / 2;
        float posY = mCenterY - height / 2;

        float r = rx;

        float vertexRatio;
        if (r / Math.min(width / 2, height / 2) > 0.5) {
            float percentage = ((r / Math.min(width / 2, height / 2)) - 0.5f) / 0.4f;
            float clampedPer = Math.min(1, percentage);
            vertexRatio = 1.f - (1 - 1.104f / 1.2819f) * clampedPer;
        } else {
            vertexRatio = 1.f;
        }

        float controlRatio;
        if (r / Math.min(width / 2, height / 2) > 0.6) {
            float percentage = ((r / Math.min(width / 2, height / 2)) - 0.6f) / 0.3f;
            float clampedPer = Math.min(1, percentage);
            controlRatio = 1 + (0.8717f / 0.8362f - 1) * clampedPer;
        } else {
            controlRatio = 1;
        }

        path.moveTo(posX + width / 2, posY);
        if (!tr) {
            path.lineTo(posX + width, posY);
        } else {

            path.lineTo(posX + Math.max(width / 2, width - r / 100.0f * 128.19f * vertexRatio), posY);
            path.cubicTo(posX + width - r / 100.f * 83.62f * controlRatio, posY, posX + width - r / 100.f * 67.45f, posY + r / 100.f * 4.64f, posX + width - r / 100.f * 51.16f, posY + r / 100.f * 13.36f);
            path.cubicTo(posX + width - r / 100.f * 34.86f, posY + r / 100.f * 22.07f, posX + width - r / 100.f * 22.07f, posY + r / 100.f * 34.86f, posX + width - r / 100.f * 13.36f, posY + r / 100.f * 51.16f);
            path.cubicTo(posX + width - r / 100.f * 4.64f, posY + r / 100.f * 67.45f, posX + width, posY + r / 100.f * 83.62f * controlRatio, posX + width, posY + Math.min(height / 2, r / 100.f * 128.19f * vertexRatio));
        }


        if (!br) {
            path.lineTo(posX + width, posY + height);
        } else {
            path.lineTo(posX + width, posY + Math.max(height / 2, height - r / 100.f * 128.19f * vertexRatio));
            path.cubicTo(posX + width, posY + height - r / 100.f * 83.62f * controlRatio, posX + width - r / 100.f * 4.64f, posY + height - r / 100.f * 67.45f, posX + width - r / 100.f * 13.36f, posY + height - r / 100.f * 51.16f);
            path.cubicTo(posX + width - r / 100.f * 22.07f, posY + height - r / 100.f * 34.86f, posX + width - r / 100.f * 34.86f, posY + height - r / 100.f * 22.07f, posX + width - r / 100.f * 51.16f, posY + height - r / 100.f * 13.36f);
            path.cubicTo(posX + width - r / 100.f * 67.45f, posY + height - r / 100.f * 4.64f, posX + width - r / 100.f * 83.62f * controlRatio, posY + height, posX + Math.max(width / 2, width - r / 100.f * 128.19f * vertexRatio), posY + height);

        }


        if (!bl) {
            path.lineTo(posX, posY + height);
        } else {
            path.lineTo(posX + Math.min(width / 2, r / 100.f * 128.19f * vertexRatio), posY + height);
            path.cubicTo(posX + r / 100.f * 83.62f * controlRatio, posY + height, posX + r / 100.f * 67.45f, posY + height - r / 100.f * 4.64f, posX + r / 100.f * 51.16f, posY + height - r / 100.f * 13.36f);
            path.cubicTo(posX + r / 100.f * 34.86f, posY + height - r / 100.f * 22.07f, posX + r / 100.f * 22.07f, posY + height - r / 100.f * 34.86f, posX + r / 100.f * 13.36f, posY + height - r / 100.f * 51.16f);
            path.cubicTo(posX + r / 100.f * 4.64f, posY + height - r / 100.f * 67.45f, posX, posY + height - r / 100.f * 83.62f * controlRatio, posX, posY + Math.max(height / 2, height - r / 100.f * 128.19f * vertexRatio));

        }

        if (!tl) {
            path.lineTo(posX, posY);
        } else {
            path.lineTo(posX, posY + Math.min(height / 2, r / 100.f * 128.19f * vertexRatio));
            path.cubicTo(posX, posY + r / 100.f * 83.62f * controlRatio, posX + r / 100.f * 4.64f, posY + r / 100.f * 67.45f, posX + r / 100.f * 13.36f, posY + r / 100.f * 51.16f);
            path.cubicTo(posX + r / 100.f * 22.07f, posY + r / 100.f * 34.86f, posX + r / 100.f * 34.86f, posY + r / 100.f * 22.07f, posX + r / 100.f * 51.16f, posY + r / 100.f * 13.36f);
            path.cubicTo(posX + r / 100.f * 67.45f, posY + r / 100.f * 4.64f, posX + r / 100.f * 83.62f * controlRatio, posY, posX + Math.min(width / 2, r / 100.f * 128.19f * vertexRatio), posY);

        }


        path.close();

        return path;
    }
}
