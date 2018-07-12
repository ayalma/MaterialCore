package material.core.shadow

import android.graphics.*
import android.support.v8.renderscript.Allocation
import android.support.v8.renderscript.Element
import android.support.v8.renderscript.RenderScript
import android.support.v8.renderscript.ScriptIntrinsicBlur
import android.view.View
import material.core.internal.MathUtils
import material.core.internal.WeakHashSet
import material.core.view.RoundedCornersView

/**
 * Created by ali on 7/12/2018.
 */
object ShadowGenerator {
    private var renderScript: Any? = null
    private var blurShader: Any? = null
    private val paint = Paint()
    private var software = false
    private val roundRect = RectF()
    private val shadowSet = WeakHashSet()

    private fun blur(bitmap: Bitmap, radius: Float) {
        if (software) {
            blurSoftware(bitmap, radius)
        } else {
            blurRenderScript(bitmap, radius)
        }
    }

    private fun blurSoftware(bitmap: Bitmap, radius: Float) {
        val width = bitmap.width
        val height = bitmap.height
        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val halfResult = IntArray(width * height)
        val rad = Math.ceil(radius.toDouble()).toInt()
        val rad2plus1 = rad * 2 + 1
        for (y in 0 until height) {
            for (x in 0 until width) {
                var sumBlack = 0
                var sumAlpha = 0
                for (i in -rad..rad) {
                    val pixel = pixels[y * width + MathUtils.constrain(x + i, 0, width - 1)]
                    sumBlack += pixel and 0xff
                    sumAlpha += pixel shr 24 and 0xff
                }
                val blurredBlack = sumBlack / rad2plus1
                val blurredAlpha = sumAlpha / rad2plus1
                halfResult[y * width + x] = Color.argb(blurredAlpha, blurredBlack, blurredBlack, blurredBlack)
            }
        }
        for (x in 0 until width) {
            for (y in 0 until height) {
                var sumBlack = 0
                var sumAlpha = 0
                for (i in -rad..rad) {
                    val pixel = halfResult[MathUtils.constrain(y + i, 0, height - 1) * height + x]
                    sumBlack += pixel and 0xff
                    sumAlpha += pixel shr 24 and 0xff
                }
                val blurredBlack = sumBlack / rad2plus1
                val blurredAlpha = sumAlpha / rad2plus1
                pixels[y * width + x] = Color.argb(blurredAlpha, blurredBlack, blurredBlack, blurredBlack)
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    }

    private fun blurRenderScript(bitmap: Bitmap, radius: Float) {
        val inAllocation = Allocation.createFromBitmap(renderScript as RenderScript, bitmap,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT)
        val outAllocation = Allocation.createTyped(renderScript as RenderScript, inAllocation.type)

        (blurShader as ScriptIntrinsicBlur).setRadius(radius)
        (blurShader as ScriptIntrinsicBlur).setInput(inAllocation)
        (blurShader as ScriptIntrinsicBlur).forEach(outAllocation)

        outAllocation.copyTo(bitmap)
    }

    @JvmStatic
    fun generateShadow(view: View, elevation: Float): Shadow {
        var elevation = elevation
        elevation = MathUtils.constrain(elevation, 0f, 25f)

        if (!software && renderScript == null) {
            try {
                renderScript = RenderScript.create(view.context)
                blurShader = ScriptIntrinsicBlur.create(renderScript as RenderScript, Element.U8_4(renderScript as RenderScript))
            } catch (ignore: Error) {
                software = true
            }

        }

        val roundedCornersView = view as RoundedCornersView

        val e = Math.ceil(elevation.toDouble()).toInt()
        val c = Math.max(e.toFloat(), roundedCornersView.cornerRadius).toInt()

        for (o in shadowSet) {
            val s = o as Shadow
            if (s != null && s.elevation == elevation && s.cornerRadius == c)
                return s
        }

        val bitmap: Bitmap
        val bitmapSize = e * 2 + 2 * c + 1
        bitmap = Bitmap.createBitmap(bitmapSize, bitmapSize, Bitmap.Config.ARGB_8888)

        val shadowCanvas = Canvas(bitmap)
        paint.style = Paint.Style.FILL
        paint.color = -0x1

        roundRect.set(e.toFloat(), e.toFloat(), (bitmapSize - e).toFloat(), (bitmapSize - e).toFloat())
        shadowCanvas.drawRoundRect(roundRect, c.toFloat(), c.toFloat(), paint)

        blur(bitmap, elevation)

        val shadow = Shadow(bitmap, elevation, c, view.context.resources.displayMetrics.density)
        shadowSet.add(shadow)
        return shadow
    }
}