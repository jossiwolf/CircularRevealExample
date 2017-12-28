package de.jw.circularrevealsharedelementexample.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import de.jw.circularrevealsharedelementexample.R


/**
 * RoundedColorView
 * View that creates a colored circle
 * Based on https://github.com/pavlospt/RoundedLetterView/blob/master/roundedletterview/src/main/java/com/github/pavlospt/roundedletterview/RoundedLetterView.java
 * Changes made: No letter is shown in the circle
 */
class RoundedColorView : View {

    private val DEFAULT_BACKGROUND_COLOR = Color.CYAN
    private val DEFAULT_VIEW_SIZE = 48

    private var mBackgroundColor = DEFAULT_BACKGROUND_COLOR

    private var mBackgroundPaint: Paint? = null
    private var mInnerRectF: RectF? = null
    private var mViewSize: Float = 0F

    /**
     * Sets the view's background color attribute value.
     * @param backgroundColor The background color attribute value to use.
     */
    override fun setBackgroundColor(backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        invalidatePaints()
    }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.RoundedColorView, defStyle, 0)

        mBackgroundColor = a.getColor(R.styleable.RoundedColorView_backgroundColor, DEFAULT_BACKGROUND_COLOR)

        a.recycle()

        //Background Paint
        mBackgroundPaint = Paint()
        mBackgroundPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mBackgroundPaint!!.style = Paint.Style.FILL
        mBackgroundPaint!!.color = mBackgroundColor

        mInnerRectF = RectF()
    }

    private fun invalidatePaints() {
        mBackgroundPaint!!.color = mBackgroundColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec)
        val height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec)
        mViewSize = Math.min(width, height).toFloat()

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        mInnerRectF!!.set(0F, 0F, mViewSize, mViewSize)
        mInnerRectF!!.offset((width - mViewSize) / 2, (height - mViewSize) / 2)

        canvas.drawOval(mInnerRectF, mBackgroundPaint)
    }
}