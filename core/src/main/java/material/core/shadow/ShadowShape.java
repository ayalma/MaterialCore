package material.core.shadow;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import material.core.animation.Style;

/**
 * Created by ali on 7/12/2018.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({ShadowShape.RECT,ShadowShape.ROUND_RECT,ShadowShape.CIRCLE})
public @interface ShadowShape {
    int RECT = 0;
    int ROUND_RECT = 1;
    int CIRCLE = 2;
}
