package material.core;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.view.LayoutInflater;

public class MaterialContextWrapper extends ContextWrapper {
    private LayoutInflater inflater;
    private MaterialResources resources;

    public static Context wrap(Context context) {
        if (context instanceof MaterialContextWrapper || context instanceof MaterialContextThemeWrapper)
            return context;
        return new MaterialContextWrapper(context);
    }

    private MaterialContextWrapper(Context base) {
        super(base);
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
                inflater = LayoutInflater.from(getBaseContext()).cloneInContext(this);
            }
            return inflater;
        }
        return super.getSystemService(name);
    }
}
