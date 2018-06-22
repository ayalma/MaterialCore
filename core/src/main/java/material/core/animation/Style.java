package material.core.animation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by ali on 6/22/2018.
 */

@Retention(RetentionPolicy.SOURCE)
@IntDef({Style.none,Style.fade,Style.pop,Style.fly,Style.slide,Style.brightnessSaturationFade,Style.progressWidth})
public @interface Style {
    int  none = 0;
    int fade = 1;
    int pop = 2;
    int fly = 3;
    int slide = 4;
    int brightnessSaturationFade = 5;
    int progressWidth = 6;
}
