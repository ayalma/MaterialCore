package material.core.animation;

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import material.R
import material.core.shadow.ShadowView



class AnimUtils {


    interface AnimatorFactory {
        fun getAnimator(): Animator
    }

    companion object {
        @JvmStatic
        fun getFadeInAnimator(): ValueAnimator {
            val animator = ViewAnimator()
            animator.interpolator = DecelerateInterpolator()
            animator.setOnSetupValuesListener({
                animator.target?.let {
                    if (it.visibility != View.VISIBLE) it.alpha = 0f
                    animator.setFloatValues(it.alpha, 1f)
                    animator.setDuration((200 * (1 - it.alpha)).toLong())
                }
            })
            animator.addUpdateListener { valueAnimator ->
                animator.target?.let {
                    it.alpha = valueAnimator.animatedValue as Float
                }
            }
            return animator
        }
        @JvmStatic
        fun getFadeOutAnimator(): ValueAnimator {
            val animator = ViewAnimator()
            animator.interpolator = DecelerateInterpolator()
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    animator.setFloatValues(view.alpha, 0f)
                    animator.setDuration(((200 * view.alpha).toLong()))
                }
            })
            animator.addUpdateListener { valueAnimator ->
                animator.target?.let {
                    it.alpha = valueAnimator.animatedValue as Float
                }
            }
            return animator
        }
        @JvmStatic
        fun getPopInAnimator(): Animator {
            val animator = ViewAnimator();
            animator.interpolator = DecelerateInterpolator();
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    if (view.visibility != View.VISIBLE) view.alpha = 0f
                    animator.setFloatValues(view.alpha, 1f)
                    animator.setDuration((200 * (1 - view.alpha)).toLong())
                }
            })
            animator.addUpdateListener({ valueAnimator ->
                animator.target?.let { view ->
                    view.alpha = valueAnimator.animatedValue as Float
                    view.scaleX = valueAnimator.animatedValue as Float
                    view.scaleY = valueAnimator.animatedValue as Float
                }
            })
            return animator;
        }
        @JvmStatic
        fun getPopOutAnimator(): Animator {
            val animator = ViewAnimator()
            animator.interpolator = DecelerateInterpolator();
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    animator.setFloatValues(view.alpha, 0f)
                    animator.setDuration((200 * view.alpha).toLong())
                }
            })
            animator.addUpdateListener({ valueAnimator ->
                animator.target?.let { view ->
                    view.alpha = valueAnimator.animatedValue as Float
                    view.scaleX = valueAnimator.animatedValue as Float
                    view.scaleY = valueAnimator.animatedValue as Float
                }
            })
            return animator;
        }
        @JvmStatic
        fun getFlyInAnimator(): ValueAnimator {
            val animator = ViewAnimator();
            animator.interpolator = LinearOutSlowInInterpolator();
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    if (view.visibility != View.VISIBLE) view.alpha = 0f
                    animator.setFloatValues(view.alpha, 1f)
                    animator.setDuration((200 * (1 - view.alpha)).toLong())
                }
            });
            animator.addUpdateListener({ valueAnimator ->
                animator.target?.let { view ->
                    view.alpha = valueAnimator.animatedValue as Float
                    view.translationY = Math.min(view.height / 2f, view.resources.getDimension(R.dimen.material_1dip) * 50.0f) * (1 - valueAnimator.animatedValue as Float)
                }
            });
            return animator
        }
        @JvmStatic
        fun getFlyOutAnimator(): ValueAnimator {

            val animator = ViewAnimator()
            animator.setInterpolator(FastOutLinearInInterpolator());
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    animator.setFloatValues(view.alpha, 0f);
                    animator.setDuration(((200 * view.alpha).toLong()));
                }


            });
            animator.addUpdateListener({ valueAnimator ->
                animator.target?.let { view ->
                    view.alpha = valueAnimator.animatedValue as Float
                    view.translationY = Math.min(view.height / 2f, view.resources.getDimension(R.dimen.material_1dip) * 50.0f) * (1 - valueAnimator.animatedValue as Float)
                }
            })
            return animator
        }
        @JvmStatic
        fun getSlideInAnimator(): ValueAnimator {
            val animator = ViewAnimator()
            animator.interpolator = LinearOutSlowInInterpolator();
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    animator.setFloatValues(view.translationY, 0f)
                    var height = view.measuredHeight
                    view.layoutParams?.let { layoutParams ->
                        if (layoutParams is ViewGroup.MarginLayoutParams) height += layoutParams.bottomMargin
                        animator.duration = (200 * Math.abs(view.translationY / height)).toLong()
                    }
                }
            })
            animator.addUpdateListener({ valueAnimator ->
                animator.target?.translationY = valueAnimator.animatedValue as Float
            })
            return animator
        }
        @JvmStatic
        fun getSlideOutAnimator() = getSlideOutAnimator(Gravity.BOTTOM);
        @JvmStatic
        fun getSlideOutAnimator(gravity: Int): ValueAnimator {
            val animator = ViewAnimator();
            animator.interpolator = FastOutLinearInInterpolator();
            animator.setOnSetupValuesListener({
                animator.target?.let { view ->
                    var height = view.measuredHeight
                    val top = (gravity.and(Gravity.BOTTOM)) == Gravity.BOTTOM
                    view.layoutParams?.let { layoutParams ->
                        if (layoutParams is ViewGroup.MarginLayoutParams)
                            height += if (top) layoutParams.bottomMargin else layoutParams.topMargin
                        animator.setFloatValues(view.translationY, if (top) height.toFloat() else -height.toFloat())
                        animator.duration = (200 * (1 - Math.abs(view.translationY / height))).toLong()
                    }
                }
            })
            animator.addUpdateListener({
                animator.target?.translationY = it.animatedValue as Float
            })
            return animator
        }
        @JvmStatic
        fun getBrightnessSaturationFadeInAnimator(): Animator {
            val animator = ViewAnimator()
            val interpolator = AccelerateDecelerateInterpolator()
            animator.interpolator = interpolator
            animator.setOnSetupValuesListener {
                animator.setFloatValues(0f, 1f) // TODO: start values
                animator.duration = 800
            }
            val saturationMatrix = ColorMatrix();
            val brightnessMatrix = ColorMatrix();
            animator.addUpdateListener {
                animator.target?.let { view ->

                    saturationMatrix.setSaturation(animator.animatedValue as Float)
                    val scale = 2 - interpolator.getInterpolation(Math.min(animator.animatedFraction * 4 / 3, 1f));
                    brightnessMatrix.setScale(scale, scale, scale, 1f)
                    saturationMatrix.preConcat(brightnessMatrix)
                    (view as ImageView).colorFilter = ColorMatrixColorFilter(saturationMatrix);
                    view.setAlpha(interpolator.getInterpolation(Math.min(animator.animatedFraction * 2, 1f)));
                }

            }
            return animator;
        }
        @JvmStatic
        fun getBrightnessSaturationFadeOutAnimator(): Animator {
            val animator = ViewAnimator()
            val interpolator = AccelerateDecelerateInterpolator()
            animator.interpolator = interpolator
            animator.setOnSetupValuesListener({
                animator.setFloatValues(1f, 0f)
                animator.duration = 800
            })
            animator.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
                val saturationMatrix = ColorMatrix();
                val brightnessMatrix = ColorMatrix();
                override fun onAnimationUpdate(animation: ValueAnimator?) {
                    animator.target?.let { view ->
                        saturationMatrix.setSaturation(animator.animatedValue as Float)
                        val scale = 2 - interpolator.getInterpolation(Math.min((1 - animator.animatedFraction) * 4f / 3f, 1f));
                        brightnessMatrix.setScale(scale, scale, scale, 1f)
                        saturationMatrix.preConcat(brightnessMatrix);
                        (view as ImageView).colorFilter = ColorMatrixColorFilter(saturationMatrix)
                        view.alpha = interpolator.getInterpolation(Math.min((1 - animator.animatedFraction) * 2f, 1f));
                    }
                }

            })
            return animator
        }
        @JvmStatic
        fun lerpColor(interpolation: Float, val1: Int, val2: Int): Float {
            /* int a =(int) MathUtils . lerp (val1 > > 24, val2 >> 24, interpolation);
             int r =(int) MathUtils . lerp ((val1 > > 16) & 0xff, (val2 >> 16) & 0xff, interpolation);
             int g =(int) MathUtils . lerp ((val1 > > 8) & 0xff, (val2 >> 8) & 0xff, interpolation);
             int b =(int) MathUtils . lerp (val1 & 0xff, val2 & 0xff, interpolation);
             return Color.argb(a, r, g, b);*/
            return 1f
        }
        @JvmStatic
        fun setupElevationAnimator(stateAnimator: StateAnimator, view: ShadowView) {

            run {
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()
                val animatorListener = object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        animator.setFloatValues(view.translationZ, (view as View).resources.getDimension(R.dimen.material_translationButton))
                    }
                }
                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }
                stateAnimator.addState(intArrayOf(android.R.attr.state_pressed), animator, animatorListener)
            }
            run {
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()
                val animatorListener = object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        animator.setFloatValues(view.translationZ, 0f)
                    }
                }
                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }
                stateAnimator.addState(intArrayOf(-android.R.attr.state_pressed, android.R.attr.state_enabled), animator, animatorListener)
            }
            run {
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()
                val animatorListener = object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        animator.setFloatValues(view.elevation, 0f)
                    }
                }
                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }
                stateAnimator.addState(intArrayOf(android.R.attr.state_enabled), animator, animatorListener)
            }
            run {
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()
                val animatorListener = object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        animator.setFloatValues(view.translationZ, -view.elevation)
                    }
                }
                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }
                stateAnimator.addState(intArrayOf(-android.R.attr.state_enabled), animator, animatorListener)
            }
           /* run{
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200;
                animator.interpolator = FastOutSlowInInterpolator();

                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }

                stateAnimator.addState(arrayOf(android.R.attr.state_pressed), animator, object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        animator.setFloatValues(view.translationZ, (view as View).resources.getDimension(R.dimen.material_translationButton))
                    }
                })
            }
            run{
                val animator = ValueAnimator.ofFloat(0f, 0f);
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()


                animator.addUpdateListener { animation ->
                    view.translationZ = animation.animatedValue as Float
                }

                stateAnimator.addState(arrayOf(-android.R.attr.state_pressed, android.R.attr.state_enabled), animator, object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        animator.setFloatValues(view.translationZ, 0f)
                    }
                })
            }
            run{
                val animator = ValueAnimator.ofFloat(0f, 0f)
                animator.duration = 200
                animator.interpolator = FastOutSlowInInterpolator()
                animator.addUpdateListener { animation -> view.translationZ = animation.animatedValue as Float }
                stateAnimator.addState(arrayOf( android.R.attr.state_enabled ), animator, object : AnimatorListenerAdapter(){
                    override fun onAnimationStart(animation: Animator?) {
                        animator.setFloatValues(view.elevation, 0f);
                    }
                })
            }
            run {
                val animator = ValueAnimator.ofFloat(0f, 0f);
                animator.duration = 200;
                animator.interpolator = FastOutSlowInInterpolator ()
                animator.addUpdateListener({animation -> view.translationZ = animation.animatedValue as Float });
                stateAnimator.addState(arrayOf( -android.R.attr.state_enabled ), animator, object : AnimatorListenerAdapter(){
                    override fun onAnimationStart(animation: Animator?) {
                        animator.setFloatValues(view.translationZ, -view.elevation);
                    }
                })
            }*/
        }
        @JvmStatic()
        fun get(animIn: Boolean, @Style type: Int): Animator? {
            return when (type) {
                Style.fade -> if (animIn) getFadeInAnimator() else getFadeOutAnimator()
                Style.pop -> if (animIn) getPopInAnimator() else getPopOutAnimator()
                Style.brightnessSaturationFade -> if (animIn) getBrightnessSaturationFadeInAnimator() else getBrightnessSaturationFadeOutAnimator()
                Style.fly -> if (animIn) getFlyInAnimator() else getFlyOutAnimator()
                Style.slide -> if (animIn) getSlideInAnimator() else getSlideOutAnimator()
                Style.progressWidth -> null
                Style.none -> null
                else -> null
            }
        }
    }
}
