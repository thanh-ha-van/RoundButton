package ha.thanh.myapplication

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.Gravity


class RoundedButton(context: Context?, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private lateinit var rectF: RectF
    private val mRoundPaint = Paint()
    private var buttonCornerRadius: Float
    private val buttonBackgroundColor: Int
    private val buttonGradientStartColor: Int
    private val buttonGradientEndColor: Int
    private val mPath = Path()

    private var mShadowColor = -147483648

    init {

        gravity = Gravity.CENTER
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        val styledAttrs =
            getContext().theme.obtainStyledAttributes(attrs, R.styleable.RoundedButton, 0, 0)

        buttonCornerRadius =
            styledAttrs.getDimension(R.styleable.RoundedButton_buttonCornerRadius, 0f)
        buttonBackgroundColor = styledAttrs.getColor(
            R.styleable.RoundedButton_buttonColor,
            ContextCompat.getColor(getContext(), R.color.colorPrimary)
        )
        buttonGradientStartColor = styledAttrs
            .getColor(R.styleable.RoundedButton_buttonStartColor, -1)
        buttonGradientEndColor = styledAttrs
            .getColor(R.styleable.RoundedButton_buttonEndColor, -1)

        if (styledAttrs.hasValue(R.styleable.RoundedButton_buttonStartColor)) {
            mShadowColor = styledAttrs.getColor(
                R.styleable.RoundedButton_buttonStartColor,
                Color.parseColor("#8D8D8D")
            )
        }
        initPaint()
    }
    override fun onDraw(canvas: Canvas?) {
        canvas ?: return
        val offset = 0f
        val radius = buttonCornerRadius

        if (buttonCornerRadius > width / 2.toFloat()) {
            buttonCornerRadius = (width / 2).toFloat()
        }
        if (buttonCornerRadius > height / 2.toFloat()) {
            buttonCornerRadius = (height / 2).toFloat()
        }
        rectF = RectF()
        rectF.set(offset, offset, width.toFloat() - offset, height.toFloat() - offset)

        mPath.rewind()
        mPath.addRoundRect(
            rectF, buttonCornerRadius, buttonCornerRadius, Path.Direction.CCW
        )
        canvas.clipPath(mPath)
        canvas.drawRoundRect(rectF, radius, radius, mRoundPaint)
        super.onDraw(canvas)
    }

    private fun initPaint() {
        mRoundPaint.style = Paint.Style.FILL
        mRoundPaint.color = buttonBackgroundColor
        mRoundPaint.isAntiAlias = true
    }
}