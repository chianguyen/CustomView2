package org.mp.customview2

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class SmileyView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_COLOR = Color.BLACK
        private const val DEFAULT_BORDER_WIDTH = 4.0f

        const val HAPPY = 0L
        const val SAD = 1L
    }

    // 2
    private var faceColor = DEFAULT_FACE_COLOR
    private var eyesColor = DEFAULT_EYES_COLOR
    private var mouthColor = DEFAULT_MOUTH_COLOR
    private var borderColor = DEFAULT_BORDER_COLOR
    private var borderWidth = DEFAULT_BORDER_WIDTH

    private val paint = Paint()
    private val mouthPath = Path()
    private var size = 0

    //Add the happy/sad states:
    var happinessState = HAPPY
        set(state) {
            field = state
            // 4
            invalidate()
        }

    // We set up Attributes with the attrs.xml file
    init {
        paint.isAntiAlias = true
        setupAttributes(attrs)
    }

    //Get the attribute array, which includes the attributes, then assign them to the vals
    private fun setupAttributes(attrs: AttributeSet?) {
        // Obtain an array of attributes
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.SmileyView, 0, 0)

        // Extract custom attributes into member variables
        happinessState = typedArray.getInt(R.styleable.SmileyView_state, HAPPY.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.SmileyView_faceColor, DEFAULT_FACE_COLOR)
        eyesColor = typedArray.getColor(R.styleable.SmileyView_eyesColor, DEFAULT_EYES_COLOR)
        mouthColor = typedArray.getColor(R.styleable.SmileyView_mouthColor, DEFAULT_MOUTH_COLOR)
        borderColor = typedArray.getColor(R.styleable.SmileyView_borderColor, DEFAULT_BORDER_COLOR)
        borderWidth = typedArray.getDimension(R.styleable.SmileyView_borderWidth, DEFAULT_BORDER_WIDTH)

        // TypedArray objects are shared and must be recycled.
        typedArray.recycle()
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.reset()

        mouthPath.moveTo(size * 0.22f, size * 0.7f)

        //if state's happy, draw a smile mouth
        if (happinessState == HAPPY) {
            // 1
            mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f)
        }

        //if state is sad, draw sad mouth
        else {
            // 2
            mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f)
            mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f)
        }

        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(mouthPath, paint)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        var radius = size / 2f

        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        canvas.drawCircle(size/2f, size/2f, radius - borderWidth, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        // 1
        paint.color = eyesColor
        paint.style = Paint.Style.FILL

        val leftEyeRect = RectF(size * 0.32f, size * 0.23f, size * 0.43f, size * 0.50f)
        canvas.drawOval(leftEyeRect, paint)

        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.68f, size * 0.50f)
        canvas.drawOval(rightEyeRect, paint)
    }

    //measure and fit the view to its parent view
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        // use MIN to calculate the smallest dimensions of the view
        // Min(a, b) = a if a is smaller, b if b is smaller
        size = Math.min(measuredWidth, measuredHeight)

        // store the measured width and measured height of the view,
        // in this case making your view width and height equivalent ( = size)
        // In this case, width is smaller, so the dimensions is = width
        setMeasuredDimension(size, size)

    }



}