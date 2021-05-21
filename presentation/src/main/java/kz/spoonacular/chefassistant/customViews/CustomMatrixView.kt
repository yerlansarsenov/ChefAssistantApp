package kz.spoonacular.chefassistant.customViews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kz.spoonacular.chefassistant.R
import java.util.*

/**
 * Created by Sarsenov Yerlan on 26.01.2021.
 */
class CustomMatrixView : View {

    companion object {
        val RANDOM = Random()
    }
    private var myWidth = 0
    private var myHeight = 0
    lateinit var canvas: Canvas
    lateinit var canvasBitmap: Bitmap
    private var fontSize = 40
    private var columnSize = 0
    private var chars = "0123456789".toCharArray()
    lateinit var txtPosByColumn: Array<Int>
    private var paintTxt = Paint()
    private var paintBg = Paint()
    private var paintBgBmp = Paint()
    private var paintInitBg = Paint()

    private fun init() {
        paintTxt.style = Paint.Style.FILL
        paintTxt.color = Color.GREEN
        paintTxt.textSize = fontSize.toFloat()
        paintTxt.typeface = ResourcesCompat.getFont(context, R.font.pt_mono)
        paintBg.style = Paint.Style.FILL
        paintBg.color = Color.BLACK
        paintBg.alpha = 5

        paintBgBmp.color = Color.BLACK

        paintInitBg.style = Paint.Style.FILL
        paintInitBg.color = Color.BLACK
        paintInitBg.alpha = 255
    }

    constructor(context: Context, attr: AttributeSet) : super(context, attr) {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        myWidth = w
        myHeight = h
        canvasBitmap = Bitmap.createBitmap(myWidth, myHeight, Bitmap.Config.ARGB_8888)
        canvas = Canvas(canvasBitmap)
        canvas.drawRect(0F, 0F, myWidth.toFloat(), myHeight.toFloat(), paintInitBg)
        columnSize = myWidth / fontSize
        txtPosByColumn = Array<Int>(columnSize + 1) {
            RANDOM.nextInt(myHeight / 2 + 1)
        }
    }

    private fun drawText() {
        for (i in txtPosByColumn.indices) {
            canvas.drawText("" + chars[RANDOM.nextInt(chars.size)],
                (i * fontSize).toFloat(), (txtPosByColumn[i] * fontSize).toFloat(), paintTxt)
            if (txtPosByColumn[i] * fontSize > myHeight && Math.random() > 0.975)
                txtPosByColumn[i] = 0
            txtPosByColumn[i]++
        }
    }

    private fun drawCanvas() {
        canvas.drawRect(0F, 0F, myWidth.toFloat(), myHeight.toFloat(), paintBg)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawBitmap(canvasBitmap, 0F, 0F, paintBgBmp)
        drawCanvas()
        drawText()
        invalidate()
    }

}
