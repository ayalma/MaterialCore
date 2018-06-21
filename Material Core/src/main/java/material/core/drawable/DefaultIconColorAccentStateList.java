package material.core.drawable;

import android.content.Context;
import android.content.res.ColorStateList;

import material.R;
import material.core.Material;


public class DefaultIconColorAccentStateList extends ColorStateList {
    public DefaultIconColorAccentStateList(Context context) {
        super(new int[][]{
                new int[]{-android.R.attr.state_enabled},
                new int[]{R.attr.material_state_invalid},
                new int[]{android.R.attr.state_checked},
                new int[]{android.R.attr.state_activated},
                new int[]{android.R.attr.state_selected},
                new int[]{android.R.attr.state_focused},
                new int[]{}
        }, new int[]{
                Material.getThemeColor(context, R.attr.material_colorDisabled),
                Material.getThemeColor(context, R.attr.material_colorError),
                Material.getThemeColor(context, R.attr.colorAccent),
                Material.getThemeColor(context, R.attr.colorAccent),
                Material.getThemeColor(context, R.attr.colorAccent),
                Material.getThemeColor(context, R.attr.colorAccent),
                Material.getThemeColor(context, R.attr.material_iconColor)
        });
    }
}
