package ru.serget.mydrivecar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import io.reactivex.rxjava3.core.Observable

class PaintView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {


    private val pathList = ArrayList<Path>()
    private var path = Path()
    private var paintBrush = Paint()

    companion object{
       private val xyList = ArrayList<Pair<Float, Float>>()
        val observerXYList: Observable<Pair<Float, Float>> = Observable.create { emit ->
            xyList.forEach {
                emit.onNext(it)
                Thread.sleep(50L)
            }
            emit.onComplete()
        }
    }

    init {
        paintBrush.isAntiAlias = true
        paintBrush.color = Color.GRAY
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.reset()
                pathList.clear()
                xyList.clear()
                xyList.add(Pair(x, y))
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
                xyList.add(Pair(x, y))
                pathList.add(path)
            }
            MotionEvent.ACTION_UP -> {
                // кинуть observable в модель
            }
        }
        postInvalidate()
        return false
    }

    override fun onDraw(canvas: Canvas?) {
        pathList.forEach {
            canvas?.drawPath(it, paintBrush)
            invalidate()
        }
    }
}