package material.core.shadow

import android.graphics.*
import android.view.View

/**
 * Created by ali on 7/12/2018.
 */
class Shadow constructor(
        private var bitmap: Bitmap,
        @JvmField
        val elevation: Float,
        @JvmField
        val cornerRadius: Int,
        private val scale: Float) {

    private val src = Rect()
    private val dst = Rect()
    private var e: Int = Math.ceil(elevation.toDouble()).toInt()
    private var xDiv: IntArray = intArrayOf(0, e + this.cornerRadius, bitmap.width - e - this.cornerRadius, bitmap.width)
    private var xDivDst: IntArray = intArrayOf((-e * scale).toInt(), (this.cornerRadius * scale).toInt(), 0, 0)
    private var yDivDst: IntArray = intArrayOf((-e * scale).toInt(), (this.cornerRadius * scale).toInt(), 0, 0)
    private var yDiv: IntArray = intArrayOf(0, e + this.cornerRadius, bitmap.height - e - this.cornerRadius, bitmap.height)

    fun draw(canvas: Canvas, view: View, paint: Paint, colorFilter: ColorFilter) {
        xDivDst[1] = Math.min(this.cornerRadius * scale, (view.width / 2).toFloat()).toInt()
        yDivDst[1] = Math.min(this.cornerRadius * scale, (view.height / 2).toFloat()).toInt()
        xDivDst[2] = Math.max(view.width - cornerRadius * scale, (view.width / 2).toFloat()).toInt()
        yDivDst[2] = Math.max(view.height - cornerRadius * scale, (view.height / 2).toFloat()).toInt()
        xDivDst[3] = (view.width + e * scale).toInt()
        yDivDst[3] = (view.height + e * scale).toInt()

        paint.colorFilter = colorFilter

        for (x in 0..2) {
            for (y in 0..2) {
                if (y == 1 && x == 1)
                    continue
                src.set(xDiv[x], yDiv[y], xDiv[x + 1], yDiv[y + 1])
                dst.set(xDivDst[x], yDivDst[y], xDivDst[x + 1], yDivDst[y + 1])
                canvas.drawBitmap(bitmap, src, dst, paint)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is Shadow && elevation == other.elevation && cornerRadius == other.cornerRadius
    }

    companion object {
        @JvmField
        val ALPHA = 47
        @JvmField
        val DEFAULT_FILTER = PorterDuffColorFilter(-0x1000000, PorterDuff.Mode.MULTIPLY)

    }
}