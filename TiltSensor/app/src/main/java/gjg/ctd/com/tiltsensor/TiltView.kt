package gjg.ctd.com.tiltsensor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.SensorEvent
import android.view.View

class TiltView(context: Context?) : View(context) {

    private val greenPaint :Paint = Paint()
    private val blackPaint :Paint = Paint()

    private var cx: Float = 0f
    private var cy: Float = 0f

    private var xCoord: Float = 0f
    private var yCoord: Float = 0f

    fun onSensorEvent(event: SensorEvent){
        yCoord = event.values[0] * 20
        xCoord = event.values[1] * 20
        invalidate()
    }


    init{
        greenPaint.color = Color.GREEN
        blackPaint.style = Paint.Style.STROKE
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        cx = w / 2f
        cy = h / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cx,cy,100f,blackPaint)
        canvas?.drawCircle(xCoord+cx,yCoord+cy,100f,greenPaint)
        canvas?.drawLine(cx-20f,cy,cx+20f,cy,blackPaint)
        canvas?.drawLine(cx,cy-20f,cx,cy+20f,blackPaint)
    }
}