package material.core;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;

public class MaterialContextThemeWrapper extends ContextThemeWrapper {
    private MaterialLayoutInflater inflater;
    private MaterialResources resources;

    public MaterialContextThemeWrapper(Context base, int theme) {
        super(base, theme);
        resources = new MaterialResources(this, getAssets(), super.getResources().getDisplayMetrics(), super.getResources().getConfiguration());
    }

    @Override
    public Resources getResources() {
        return resources;
    }

    @Override
    public Object getSystemService(String name) {
        if (LAYOUT_INFLATER_SERVICE.equals(name)) {
            if (inflater == null) {
                inflater = new MaterialLayoutInflater( LayoutInflater.from(getBaseContext()).cloneInContext(this),getBaseContext());
            }
            return inflater;
        }
        return super.getSystemService(name);
    }

}
