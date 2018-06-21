package material.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public class MaterialLayoutInflater extends LayoutInflater {
    protected MaterialLayoutInflater(Context context) {
        super(context);
    }

    protected MaterialLayoutInflater(LayoutInflater original, Context newContext) {
        super(original, newContext);
    }

    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new MaterialLayoutInflater(this, newContext);
    }

    @Override
    protected View onCreateView(String name, AttributeSet attrs) throws ClassNotFoundException {
        if (Character.isUpperCase(name.charAt(0)))
            return createView(name, "carbon.widget.", attrs);
        return super.onCreateView(name, attrs);
    }

    @Override
    protected View onCreateView(View parent, String name, AttributeSet attrs) throws ClassNotFoundException {
        if (Character.isUpperCase(name.charAt(0)))
            return createView(name, "carbon.widget.", attrs);
        return super.onCreateView(parent, name, attrs);
    }
}
