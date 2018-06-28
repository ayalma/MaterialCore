package material.core.animation

import android.animation.Animator

/**
 * Interface of a view with animations. These animations are used for changing visibility by using
 * setVisible(boolean) method.
 */
interface AnimatedView {
    /**
     * Gets the current Animator object. Works like View.getAnimation() but with animators.
     *
     * @return the current Animator object or null
     */
    fun getAnimator(): Animator

    /**
     * Gets the animation used when view's visibility is changed from VISIBLE to GONE/INVISIBLE
     *
     * @return the current out animation or null if nothing is set.
     */
    fun getOutAnimator(): Animator

    /**
     * Sets the animation used when view's visibility is changed from VISIBLE to GONE/INVISIBLE
     *
     * @param outAnim new out animation. Use null for no animation.
     */
    fun setOutAnimator(outAnim: Animator)

    /**
     * Gets the animation used when view's visibility is changed from GONE/INVISIBLE to VISIBLE
     *
     * @return the current in animation or null if nothing is set.
     */
    fun getInAnimator(): Animator

    /**
     * Sets the animation used when view's visibility is changed from GONE/INVISIBLE to VISIBLE
     *
     * @param inAnim new in animation. Use null for no animation.
     */
    fun setInAnimator(inAnim: Animator)

    /**
     * Sets visibility using set animation style.
     *
     * @param visibility one of View.VISIBLE/INVISIBLE/GONE flags
     * @return visibility animation animator
     */
    fun animateVisibility(visibility: Int):Animator
}
