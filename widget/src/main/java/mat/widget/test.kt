package mat.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.util.AttributeSet
import android.widget.Button
import material.core.shadow.ShadowView
import material.core.view.RenderingMode

/**
 * Created by ali on 7/7/2018.
 */
class btn @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Button(context, attrs, defStyleAttr) , ShadowView {
    override fun setRenderingMode(mode: RenderingMode?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRenderingMode(): RenderingMode {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getShadowShape(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hasShadow(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawShadow(canvas: Canvas?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setElevationShadowColor(shadowColor: ColorStateList?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setElevationShadowColor(color: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getElevationShadowColor(): ColorStateList {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}