package material.core.animation
import android.animation.Animator
import android.util.StateSet
import android.view.animation.Animation
import java.lang.ref.WeakReference
import java.util.*

class StateAnimator(target: AnimatedView) {
    /**
     * @hide
     */
    private var tuples = ArrayList<Tuple>()

    private var lastMatch: Tuple? = null
    /**
     * Returns the current [Animation] which is started because of a state
     * change.
     *
     * @return The currently running Animation or null if no Animation is running
     */
    private var runningAnimation: Animator? = null

    private var viewRef: WeakReference<AnimatedView>? = null

    private val mAnimationListener = object : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {

        }

        override fun onAnimationEnd(animation: Animator) {
            if (runningAnimation == animation) {
                runningAnimation = null
            }
        }

        override fun onAnimationCancel(animation: Animator) {

        }

        override fun onAnimationRepeat(animation: Animator) {

        }

    }


    internal var target: AnimatedView?
        get() = viewRef?.get()
        set(view) {
            if (target == view) {
                return
            }
            target?.let { clearTarget() }

            view?.let {
                viewRef = WeakReference(it)
            }
        }

    init {
        this.target = target
    }

    /**
     * Associates the given Animation with the provided drawable state specs so that it will be run
     * when the View's drawable state matches the specs.
     *
     * @param specs     drawable state specs to match against
     * @param animation The Animation to run when the specs match
     */
    fun addState(specs: IntArray, animation: Animator, listener: Animator.AnimatorListener) {
        val tuple = Tuple(specs, animation, listener)
        animation.addListener(mAnimationListener)
        tuples.add(tuple)
    }

    private fun clearTarget() {
        tuples.forEach {
            if (it.animation == target?.getAnimator())
                it.animation.cancel()
        }
        viewRef = null
        lastMatch = null
        runningAnimation = null
    }

    /**
     * Called by View
     */
    fun setState(state: IntArray) {
        var match: Tuple? = null
        run breaker@{
            tuples.forEach {
                if (StateSet.stateSetMatches(it.specs, state)) {
                    match = it
                    return@breaker
                }
            }
        }

        if (match == lastMatch) return

        lastMatch?.let { cancel() }

        lastMatch = match

        match?.let {m ->
            viewRef?.get()?.let {
                start(m)
            }
        }
    }

    private fun start(match: Tuple) {
        match.listener.onAnimationStart(match.animation)
        runningAnimation = match.animation
        runningAnimation?.start()
    }

    private fun cancel() {
        runningAnimation?.let { animator ->
            if (target?.getAnimator() == animator)
                animator.cancel()
        }
    }

    /**
     * If there is an animation running for a recent state change, ends it.
     *
     * This causes the
     * animation to assign the end value(s) to the View.
     */
    fun jumpToCurrentState() {
        runningAnimation?.let { animator ->
            if (target?.getAnimator() == animator)
                animator.cancel()
        }
    }

    internal class Tuple constructor(val specs: IntArray, val animation: Animator, val listener: Animator.AnimatorListener)

}
