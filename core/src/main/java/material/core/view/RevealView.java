package material.core.view;

import android.animation.Animator;

import material.core.view.RenderingModeView;

public interface RevealView extends RenderingModeView {
    float MAX_RADIUS = -1;

    Animator createCircularReveal(android.view.View hotspot, float startRadius, float finishRadius);

    Animator createCircularReveal(int x, int y, float startRadius, float finishRadius);
}
