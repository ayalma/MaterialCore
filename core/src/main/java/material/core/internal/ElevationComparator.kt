package material.core.internal

import android.view.View
import material.core.shadow.ShadowView

/**
 * Created by ali on 6/30/2018.
 */
class ElevationComparator : Comparator<View> {
    override fun compare(lhs: View?, rhs: View?): Int {
        val elevation1 = if (lhs is ShadowView) (lhs as ShadowView).getElevation() + (lhs as ShadowView).getTranslationZ() else 0f
        val elevation2 = if (rhs is ShadowView) (rhs as ShadowView).getElevation() + (rhs as ShadowView).getTranslationZ() else 0f
        return Math.signum(elevation1 - elevation2).toInt()
    }
}