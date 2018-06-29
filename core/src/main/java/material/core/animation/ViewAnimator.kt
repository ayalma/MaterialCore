/*
package material.core.animation;

import android.animation.ValueAnimator
import android.view.View

class ViewAnimator : ValueAnimator() {

    var target: View? = null

    private var listener: (() -> Unit)? = null

    fun setOnSetupValuesListener(listener: () -> Unit) {
        this.listener = listener
    }

    override fun setTarget(target: Any?) {
        super.setTarget(target)
        this.target = target as View?
    }

    override fun start() {
        listener?.invoke()
        super.start()
    }


}

*/
