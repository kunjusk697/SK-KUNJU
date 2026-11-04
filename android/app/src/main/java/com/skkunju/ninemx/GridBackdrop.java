package com.skkunju.ninemx;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class GridBackdrop extends View {
    private final Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint line = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint bar = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint streak = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint fade = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint spark = new Paint(Paint.ANTI_ALIAS_FLAG);

    public GridBackdrop(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        line.setColor(0x18FFFFFF);
        line.setStrokeWidth(1f);
        streak.setColor(0xCCFFE08A);
        streak.setStrokeCap(Paint.Cap.ROUND);
        spark.setColor(0x99F0C14B);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int h = getHeight();
        canvas.drawColor(0xFF070709);
        glow.setShader(new RadialGradient(
                w * 0.5f,
                h * 0.28f,
                w * 0.85f,
                new int[]{0x66C47A12, 0x22180C04, 0x00070709},
                new float[]{0f, 0.42f, 1f},
                Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, w, h, glow);

        float gap = dp(32);
        for (float y = 0; y < h; y += gap) {
            canvas.drawLine(0, y, w, y, line);
        }
        for (float x = 0; x < w; x += gap) {
            canvas.drawLine(x, 0, x, h, line);
        }

        float base = h * 0.80f;
        float barW = dp(26);
        float start = w * 0.58f;
        float[] heights = {0.08f, 0.14f, 0.20f, 0.32f};
        for (int i = 0; i < heights.length; i++) {
            float left = start + i * (barW + dp(12));
            float top = base - h * heights[i];
            bar.setColor(i == heights.length - 1 ? 0xFF8A6A32 : 0xFF3A342C);
            canvas.drawRoundRect(left, top, left + barW, base, dp(2), dp(2), bar);
        }

        streak.setStrokeWidth(dp(3));
        streak.setMaskFilter(new BlurMaskFilter(dp(8), BlurMaskFilter.Blur.NORMAL));
        canvas.drawLine(w * 0.48f, base - dp(4), w * 0.95f, h * 0.46f, streak);

        float[] sparks = {0.18f, 0.22f, 0.72f, 0.18f, 0.3f, 0.62f, 0.8f, 0.7f, 0.12f};
        for (int i = 0; i < sparks.length; i += 3) {
            canvas.drawCircle(w * sparks[i], h * sparks[i + 1], dp(sparks[i + 2] * 8f), spark);
        }

        fade.setShader(new LinearGradient(
                0, h * 0.62f, 0, h,
                0x00070709, 0xEE070709, Shader.TileMode.CLAMP));
        canvas.drawRect(0, h * 0.62f, w, h, fade);
    }

    private float dp(float value) {
        return value * getResources().getDisplayMetrics().density;
    }
}
