package material.core.drawable;

import android.content.Context;
import android.content.res.ColorStateList;

import material.R;
import material.core.Material;


public class DefaultTextSecondaryColorStateList extends ColorStateList {
    public DefaultTextSecondaryColorStateList(Context context) {
        super(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{R.attr.material_state_invalid},
                new int[]{}
        }, new int[]{
                Material.getThemeColor(context, android.R.attr.textColorTertiary),
                Material.getThemeColor(context, R.attr.material_colorError),
                Material.getThemeColor(context, android.R.attr.textColorSecondary)
        });
    }
}
