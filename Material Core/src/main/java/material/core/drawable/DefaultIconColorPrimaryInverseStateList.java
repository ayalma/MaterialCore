package material.core.drawable;

import android.content.Context;
import android.content.res.ColorStateList;

import material.R;
import material.core.Material;


public class DefaultIconColorPrimaryInverseStateList extends ColorStateList {
    public DefaultIconColorPrimaryInverseStateList(Context context) {
        super(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{R.attr.material_state_invalid},
                new int[]{android.R.attr.state_checked},
                new int[]{android.R.attr.state_activated},
                new int[]{android.R.attr.state_selected},
                new int[]{android.R.attr.state_focused},
                new int[]{}
        }, new int[]{
                Material.getThemeColor(context, R.attr.material_colorDisabledInverse),
                Material.getThemeColor(context, R.attr.material_colorError),
                Material.getThemeColor(context, R.attr.colorPrimary),
                Material.getThemeColor(context, R.attr.colorPrimary),
                Material.getThemeColor(context, R.attr.colorPrimary),
                Material.getThemeColor(context, R.attr.colorPrimary),
                Material.getThemeColor(context, R.attr.material_iconColorInverse)
        });
    }
}
