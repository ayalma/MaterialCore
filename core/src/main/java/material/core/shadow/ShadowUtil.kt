package material.core.shadow

import android.annotation.SuppressLint
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import material.core.Material
import material.core.shadow.ShadowShape.*
import material.core.view.RoundedCornersView

/**
 * Created by ali on 7/12/2018.
 */
object ShadowUtil {
    @JvmField
    var viewOutlineProvider: ViewOutlineProvider? = if (Material.IS_LOLLIPOP) {
        object : ViewOutlineProvider() {
            @SuppressLint("NewApi")
            override fun getOutline(view: View?, outline: Outline?) {
                val shadowShape = (view as ShadowView).shadowShape
                when (shadowShape) {
                    RECT -> outline?.setRect(0, 0, view.width, view.height)
                    ROUND_RECT -> outline?.setRoundRect(0, 0, view.width, view.height, (view as RoundedCornersView).cornerRadius)
                    CIRCLE -> outline?.setOval(0, 0, view.width, view.height)
                }
            }
        }
    } else null
}