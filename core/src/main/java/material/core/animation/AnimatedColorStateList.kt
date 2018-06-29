package material.core.animation

import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.os.Parcel
import android.os.Parcelable
import android.util.StateSet
import android.view.animation.AccelerateDecelerateInterpolator
import material.core.internal.ArgbEvaluator
import java.lang.reflect.Field
import java.util.*

/**
 * Created by ali on 6/29/2018.
 */
class AnimatedColorStateList constructor(var states: Array<IntArray>, colors: IntArray, listener: ValueAnimator.AnimatorUpdateListener?) : ColorStateList(states, colors) {
    private var currentState: IntArray? = null
    private var colorAnimation: ValueAnimator? = null
    private var animatedColor: Int = 0

    init {
        colorAnimation = ValueAnimator.ofInt(0, 0)
        colorAnimation?.setEvaluator(ArgbEvaluator())
        colorAnimation?.duration = 200
        colorAnimation?.interpolator = AccelerateDecelerateInterpolator()
        colorAnimation?.addUpdateListener { animation ->
            synchronized(this@AnimatedColorStateList) {
                animatedColor = animation.animatedValue as Int
                listener?.onAnimationUpdate(animation)
            }
        }
    }

    override fun getColorForState(stateSet: IntArray?, defaultColor: Int): Int {
        synchronized(this@AnimatedColorStateList) {
            if (Arrays.equals(stateSet, currentState) ) {
                return animatedColor
            }
        }
        return super.getColorForState(stateSet, defaultColor)
    }

    fun setState(newState: IntArray) {
        if (Arrays.equals(newState, currentState))
            return
        if (currentState != null)
            cancel()

        states.forEach {
            if (StateSet.stateSetMatches(it, newState))
            {
                val firstColor = super.getColorForState(currentState, defaultColor)
                val secondColor = super.getColorForState(newState, defaultColor)
                colorAnimation?.setIntValues(firstColor, secondColor)
                currentState = newState
                animatedColor = firstColor
                colorAnimation?.start()
                return
            }
        }


        currentState = newState
    }

    private fun cancel() {
        colorAnimation?.cancel()
    }

    fun jumpToCurrentState() {
        colorAnimation?.end()
    }
    companion object {
        @JvmStatic
        private var mStateSpecsField: Field? = null
        @JvmStatic
        private var mColorsField: Field? = null
        @JvmStatic
        private var mDefaultColorField: Field? = null

        init {
            try {
                mStateSpecsField = ColorStateList::class.java.getDeclaredField("mStateSpecs")
                mStateSpecsField?.isAccessible = true
                mColorsField = ColorStateList::class.java.getDeclaredField("mColors")
                mColorsField?.isAccessible = true
                mDefaultColorField = ColorStateList::class.java.getDeclaredField("mDefaultColor")
                mDefaultColorField?.isAccessible = true
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            }

        }

        @JvmStatic
        fun fromList(list: ColorStateList, listener: ValueAnimator.AnimatorUpdateListener?): AnimatedColorStateList? {
            val mStateSpecs: Array<IntArray> // must be parallel to mColors
            val mColors: IntArray      // must be parallel to mStateSpecs
            val mDefaultColor: Int

            try {
                mStateSpecs = mStateSpecsField?.get(list) as Array<IntArray>
                mColors = mColorsField?.get(list) as IntArray
                mDefaultColor = mDefaultColorField?.get(list) as Int
                val animatedColorStateList = AnimatedColorStateList(mStateSpecs, mColors, listener)
                mDefaultColorField?.set(animatedColorStateList, mDefaultColor)
                return animatedColorStateList
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

            return null
        }

        @JvmStatic
        val CREATOR = object : Parcelable.Creator<AnimatedColorStateList> {
            override fun newArray(size: Int): Array<AnimatedColorStateList?> {
                return arrayOfNulls(size)
            }

            override fun createFromParcel(source: Parcel): AnimatedColorStateList? {
                val stateSpecs = arrayOfNulls<IntArray>(source.readInt())
                stateSpecs.forEachIndexed({ index: Int, _: IntArray? ->
                    stateSpecs[index] = source.createIntArray()
                })

                val colors = source.createIntArray()
                return AnimatedColorStateList.fromList(ColorStateList(stateSpecs, colors), null)
            }
        }
    }
}