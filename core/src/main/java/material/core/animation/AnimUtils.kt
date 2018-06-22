package material.core.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter
import android.media.Image
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import material.R;
import material.R.id.start
import material.core.animation.AnimUtils.Companion.getFadeOutAnimator
import material.core.shadow.ShadowView
import  material.core.animation.Style;

class AnimUtils {


    interface AnimatorFactory {
        fun getAnimator(): Animator
    }

    companion object {
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

        fun getSlideOutAnimator(): ValueAnimator {
            return getSlideOutAnimator(Gravity.BOTTOM);
        }

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

        fun getBrightnessSaturationFadeInAnimator(): Animator {
            ViewAnimator animator = new ViewAnimator();
            final AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator ();
            animator.setInterpolator(interpolator);
            animator.setOnSetupValuesListener(() -> {
                animator.setFloatValues(0, 1);  // TODO: start values
                animator.setDuration(800);
            });
            animator.addUpdateListener(new ValueAnimator . AnimatorUpdateListener () {
                ColorMatrix saturationMatrix = new ColorMatrix();
                ColorMatrix brightnessMatrix = new ColorMatrix();

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ImageView imageView =(ImageView) animator . getTarget ();
                    float fraction = animator . getAnimatedFraction ();

                    saturationMatrix.setSaturation((Float) animator . getAnimatedValue ());

                    float scale = 2-interpolator.getInterpolation(Math.min(fraction * 4 / 3, 1));
                    brightnessMatrix.setScale(scale, scale, scale, 1);

                    saturationMatrix.preConcat(brightnessMatrix);
                    imageView.setColorFilter(new ColorMatrixColorFilter (saturationMatrix));
                    imageView.setAlpha(interpolator.getInterpolation(Math.min(fraction * 2, 1)));
                }
            });
            return animator;
        }

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

        fun lerpColor(interpolation: Float, val1: Int, val2: Int): Float {
            int a =(int) MathUtils . lerp (val1 > > 24, val2 >> 24, interpolation);
            int r =(int) MathUtils . lerp ((val1 > > 16) & 0xff, (val2 >> 16) & 0xff, interpolation);
            int g =(int) MathUtils . lerp ((val1 > > 8) & 0xff, (val2 >> 8) & 0xff, interpolation);
            int b =(int) MathUtils . lerp (val1 & 0xff, val2 & 0xff, interpolation);
            return Color.argb(a, r, g, b);
        }

        fun setupElevationAnimator(stateAnimator: StateAnimator, view: ShadowView) {
            {
                final ValueAnimator animator = ValueAnimator.ofFloat(0, 0);
                animator.setDuration(200);
                animator.setInterpolator(new FastOutSlowInInterpolator ());
                Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animator.setFloatValues(view.getTranslationZ(), ((View) view).getResources().getDimension(R.dimen.material_translationButton));
                    }
                };
                animator.addUpdateListener(animation -> view.setTranslationZ((Float) animation.getAnimatedValue()));
                stateAnimator.addState(new int []{ android.R.attr.state_pressed }, animator, animatorListener);
            }
            {
                final ValueAnimator animator = ValueAnimator.ofFloat(0, 0);
                animator.setDuration(200);
                animator.setInterpolator(new FastOutSlowInInterpolator ());
                Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animator.setFloatValues(view.getTranslationZ(), 0);
                    }
                };
                animator.addUpdateListener(animation -> view.setTranslationZ((Float) animation.getAnimatedValue()));
                stateAnimator.addState(new int []{ -android.R.attr.state_pressed, android.R.attr.state_enabled }, animator, animatorListener);
            }
            {
                final ValueAnimator animator = ValueAnimator.ofFloat(0, 0);
                animator.setDuration(200);
                animator.setInterpolator(new FastOutSlowInInterpolator ());
                Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animator.setFloatValues(view.getElevation(), 0);
                    }
                };
                animator.addUpdateListener(animation -> view.setTranslationZ((Float) animation.getAnimatedValue()));
                stateAnimator.addState(new int []{ android.R.attr.state_enabled }, animator, animatorListener);
            }
            {
                final ValueAnimator animator = ValueAnimator.ofFloat(0, 0);
                animator.setDuration(200);
                animator.setInterpolator(new FastOutSlowInInterpolator ());
                Animator.AnimatorListener animatorListener = new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        animator.setFloatValues(view.getTranslationZ(), -view.getElevation());
                    }
                };
                animator.addUpdateListener(animation -> view.setTranslationZ((Float) animation.getAnimatedValue()));
                stateAnimator.addState(new int []{ -android.R.attr.state_enabled }, animator, animatorListener);
            }
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
