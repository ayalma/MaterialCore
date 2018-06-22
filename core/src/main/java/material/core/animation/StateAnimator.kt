package material.core.animation;

import android.animation.Animator;
import android.util.StateSet;
import android.view.View;
import android.view.animation.Animation;
import material.R.id.start

import java.lang.ref.WeakReference;
import kotlin.collections.ArrayList

class StateAnimator(target: AnimatedView) {
    private val mTuples = ArrayList<Tuple>()

    private var lastMatch: Tuple? = null
    private var runningAnimation: Animator? = null
    private var viewRef: WeakReference<AnimatedView>? = null

    init {
        setTarget(target)
    }
    private val mAnimationListener = object : Animator.AnimatorListener {
        override fun onAnimationCancel(animation: Animator?) {}

        override fun onAnimationStart(animation: Animator?) {}

        override fun onAnimationRepeat(animation: Animator?) {}

        override fun onAnimationEnd(animation: Animator?) {
            if (runningAnimation == animation) {
                runningAnimation = null
            }
        }
    }

    /**
     * Associates the given Animation with the provided drawable state specs so that it will be run
     * when the View's drawable state matches the specs.
     *
     * @param specs     drawable state specs to match against
     * @param animation The Animation to run when the specs match
     */
    fun addState(specs: Array<Int>, animation: Animator, listener: Animator.AnimatorListener) {
        val tuple = Tuple(specs, animation, listener);
        animation.addListener(mAnimationListener);
        mTuples.add(tuple);
    }

    /**
     * Returns the current {@link Animation} which is started because of a state
     * change.
     *
     * @return The currently running Animation or null if no Animation is running
     */
    fun getRunningAnimation(): Animator? = runningAnimation

    fun getTarget(): AnimatedView? = viewRef?.get()

    fun setTarget(view: AnimatedView?) {
        val current = getTarget();
        if (current == view) {
            return;
        }
        if (current != null) {
            clearTarget();
        }
        if (view != null) {
            viewRef = WeakReference(view);
        }
    }

    fun clearTarget() {
        mTuples.forEach {
            if (it.animation == getTarget())
                it.animation.cancel()
        }
        viewRef = null
        lastMatch = null
        runningAnimation = null
    }

    /**
     * Called by View
     */
    fun setState(state: Array<Int>) {
        var match: Tuple? = null

        mTuples.forEach {
            if (StateSet.stateSetMatches(it.mSpecs.toIntArray(), state.toIntArray())) match = it;
        }

        if (match == lastMatch) return

        lastMatch?.let {
            cancel()
        }

        lastMatch = match;

        match?.let { m ->
            viewRef?.get()?.let {
                if ((it as View).visibility == View.VISIBLE)
                    start(m)
            }
        }
    }

    fun start(match: Tuple) {
        match.listener.onAnimationStart(match.animation)
        runningAnimation = match.animation;
        runningAnimation?.start()
    }

    private fun cancel() {
        runningAnimation?.let { anim ->
            getTarget()?.let {
                if (it.getAnimator() == anim) anim.cancel()
            }
            runningAnimation = null
        }
    }

    /**
     * @hide
     */
    fun getTuples(): ArrayList<Tuple> = mTuples;

    /**
     * If there is an animation running for a recent state change, ends it. <p> This causes the
     * animation to assign the end value(s) to the View.
     */
    fun jumpToCurrentState() {
        if (runningAnimation != null) {
            val view = getTarget();
            if (view != null && view.getAnimator() == runningAnimation) {
                runningAnimation?.cancel();
            }
        }
    }

    inner class Tuple(val mSpecs: Array<Int>, val animation: Animator, val listener: Animator.AnimatorListener)

    init {
        setTarget(target)
    }

}
