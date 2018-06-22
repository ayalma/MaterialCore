package material.core.animation;

import android.animation.ValueAnimator
import android.view.View

class ViewAnimator : ValueAnimator() {

     var target: View? = null
        set(value) {
            super.setTarget(value)
            field = value
        }

    private var listener: (() -> Unit)? = null

    fun setOnSetupValuesListener(listener:()->Unit)
    {
        this.listener = listener
    }

    override fun start() {
        listener?.invoke()
        super.start()
    }

}
