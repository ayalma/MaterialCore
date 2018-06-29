package material.core;

import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.TintAwareDrawable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.security.InvalidParameterException;

import material.R;
import material.core.animation.AnimUtils;
import material.core.animation.AnimatedColorStateList;
import material.core.animation.AnimatedView;
import material.core.drawable.AlphaDrawable;
import material.core.drawable.ColorStateListDrawable;
import material.core.drawable.DefaultAccentColorStateList;
import material.core.drawable.DefaultColorStateList;
import material.core.drawable.DefaultIconColorAccentInverseStateList;
import material.core.drawable.DefaultIconColorAccentStateList;
import material.core.drawable.DefaultIconColorInverseStateList;
import material.core.drawable.DefaultIconColorPrimaryInverseStateList;
import material.core.drawable.DefaultIconColorPrimaryStateList;
import material.core.drawable.DefaultIconColorStateList;
import material.core.drawable.DefaultPrimaryColorStateList;
import material.core.drawable.DefaultTextColorAccentStateList;
import material.core.drawable.DefaultTextColorPrimaryStateList;
import material.core.drawable.DefaultTextPrimaryColorInverseStateList;
import material.core.drawable.DefaultTextPrimaryColorStateList;
import material.core.drawable.DefaultTextSecondaryColorInverseStateList;
import material.core.drawable.DefaultTextSecondaryColorStateList;
import material.core.drawable.ripple.RippleDrawable;
import material.core.drawable.ripple.RippleView;
import material.core.shadow.ShadowView;
import material.core.view.AutoSizeTextMode;
import material.core.view.AutoSizeTextView;
import material.core.view.InsetView;
import material.core.view.MaxSizeView;
import material.core.view.StateAnimatorView;
import material.core.view.StrokeView;
import material.core.view.TintedView;
import material.core.view.TouchMarginView;

import static material.core.view.RevealView.MAX_RADIUS;

/**
 * Created by ali on 6/6/2018.
 */

public class Material {

    private static final long DEFAULT_REVEAL_DURATION = 200;
    private static long defaultRevealDuration = DEFAULT_REVEAL_DURATION;

    public static final boolean IS_LOLLIPOP = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH;

    public static PorterDuffXfermode CLEAR_MODE = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    public static final int INVALID_INDEX = -1;

    private Material() {
    }

    public static float getDip(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
    }

    public static float getSp(Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, context.getResources().getDisplayMetrics());
    }

    public static ColorStateList getDefaultColorStateList(View view, TypedArray a, int id) {
        if (!a.hasValue(id))
            return null;
        try {
            if (a.getColor(id, 0) != view.getResources().getColor(R.color.material_defaultColor))
                return null;
        }catch (Resources.NotFoundException e){
            return null;
        }

        Context context = view.getContext();
        int resourceId = a.getResourceId(id, 0);

        if (resourceId == R.color.material_defaultColor) {
            return new DefaultColorStateList(context);
        } else if (resourceId == R.color.material_defaultColorPrimary) {
            return new DefaultPrimaryColorStateList(context);
        } else if (resourceId == R.color.material_defaultColorAccent) {
            return new DefaultAccentColorStateList(context);
        } else if (resourceId == R.color.material_defaultIconColor) {
            return new DefaultIconColorStateList(context);
        } else if (resourceId == R.color.material_defaultIconColorInverse) {
            return new DefaultIconColorInverseStateList(context);
        } else if (resourceId == R.color.material_defaultIconColorAccent) {
            return new DefaultIconColorAccentStateList(context);
        } else if (resourceId == R.color.material_defaultIconColorAccentInverse) {
            return new DefaultIconColorAccentInverseStateList(context);
        } else if (resourceId == R.color.material_defaultIconColorPrimary) {
            return new DefaultIconColorPrimaryStateList(context);
        } else if (resourceId == R.color.material_defaultIconColorPrimaryInverse) {
            return new DefaultIconColorPrimaryInverseStateList(context);
        } else if (resourceId == R.color.material_defaultTextPrimaryColor) {
            return new DefaultTextPrimaryColorStateList(context);
        } else if (resourceId == R.color.material_defaultTextSecondaryColor) {
            return new DefaultTextSecondaryColorStateList(context);
        } else if (resourceId == R.color.material_defaultTextPrimaryColorInverse) {
            return new DefaultTextPrimaryColorInverseStateList(context);
        } else if (resourceId == R.color.material_defaultTextSecondaryColorInverse) {
            return new DefaultTextSecondaryColorInverseStateList(context);
        } else if (resourceId == R.color.material_defaultTextColorPrimary) {
            return new DefaultTextColorPrimaryStateList(context);
        } else if (resourceId == R.color.material_defaultTextColorAccent) {
            return new DefaultTextColorAccentStateList(context);
        } else if (resourceId == R.color.material_defaultRippleColor) {
            int c = Material.getThemeColor(context, R.attr.material_rippleColor);
            return ColorStateList.valueOf(0x12000000 | (c & 0xffffff));
        } else if (resourceId == R.color.material_defaultRippleColorPrimary) {
            int c = Material.getThemeColor(context, R.attr.colorPrimary);
            return ColorStateList.valueOf(0x12000000 | (c & 0xffffff));
        } else if (resourceId == R.color.material_defaultRippleColorAccent) {
            int c = Material.getThemeColor(context, R.attr.colorAccent);
            return ColorStateList.valueOf(0x12000000 | (c & 0xffffff));
        }

        return null;
    }

    public static void initDefaultBackground(View view, TypedArray a, int id) {
        ColorStateList color = getDefaultColorStateList(view, a, id);
        if (color != null)
            view.setBackgroundDrawable(new ColorStateListDrawable(AnimatedColorStateList.fromList(color, animation -> view.postInvalidate())));
    }

    public static void initDefaultTextColor(TextView view, TypedArray a, int id) {
        ColorStateList color = getDefaultColorStateList(view, a, id);
        if (color != null)
            view.setTextColor(AnimatedColorStateList.fromList(color, animation -> view.postInvalidate()));
    }

    public static void initRippleDrawable(RippleView rippleView, TypedArray a, int[] ids) {
        int material_rippleColor = ids[0];
        int material_rippleStyle = ids[1];
        int material_rippleHotspot = ids[2];
        int material_rippleRadius = ids[3];

        View view = (View) rippleView;
        if (view.isInEditMode())
            return;

        ColorStateList color = getDefaultColorStateList(view, a, material_rippleColor);
        if (color == null)
            color = a.getColorStateList(material_rippleColor);

        if (color != null) {
            RippleDrawable.Style style = RippleDrawable.Style.values()[a.getInt(material_rippleStyle, RippleDrawable.Style.Background.ordinal())];
            boolean useHotspot = a.getBoolean(material_rippleHotspot, true);
            int radius = (int) a.getDimension(material_rippleRadius, -1);

            rippleView.setRippleDrawable(RippleDrawable.create(color, style, view, useHotspot, radius));
        }
    }

    public static void initTouchMargin(TouchMarginView view, TypedArray a, int[] ids) {
        int material_touchMargin = ids[0];
        int material_touchMarginLeft = ids[1];
        int material_touchMarginTop = ids[2];
        int material_touchMarginRight = ids[3];
        int material_touchMarginBottom = ids[4];

        int touchMarginAll = (int) a.getDimension(material_touchMargin, 0);
        int left = (int) a.getDimension(material_touchMarginLeft, touchMarginAll);
        int top = (int) a.getDimension(material_touchMarginTop, touchMarginAll);
        int right = (int) a.getDimension(material_touchMarginRight, touchMarginAll);
        int bottom = (int) a.getDimension(material_touchMarginBottom, touchMarginAll);
        view.setTouchMargin(left, top, right, bottom);
    }

    public static void initInset(InsetView view, TypedArray a, int[] ids) {
        int material_inset = ids[0];
        int material_insetLeft = ids[1];
        int material_insetTop = ids[2];
        int material_insetRight = ids[3];
        int material_insetBottom = ids[4];
        int material_insetColor = ids[5];

        int insetAll = (int) a.getDimension(material_inset, InsetView.INSET_NULL);
        int left = (int) a.getDimension(material_insetLeft, insetAll);
        int top = (int) a.getDimension(material_insetTop, insetAll);
        int right = (int) a.getDimension(material_insetRight, insetAll);
        int bottom = (int) a.getDimension(material_insetBottom, insetAll);
        view.setInset(left, top, right, bottom);

        view.setInsetColor(a.getColor(material_insetColor, 0));
    }

    public static void initMaxSize(MaxSizeView view, TypedArray a, int[] ids) {
        int material_maxWidth = ids[0];
        int material_maxHeight = ids[1];

        int width = (int) a.getDimension(material_maxWidth, Integer.MAX_VALUE);
        int height = (int) a.getDimension(material_maxHeight, Integer.MAX_VALUE);
        view.setMaximumWidth(width);
        view.setMaximumHeight(height);
    }

    public static void initTint(TintedView view, TypedArray a, int[] ids) {
        int material_tint = ids[0];
        int material_tintMode = ids[1];
        int material_backgroundTint = ids[2];
        int material_backgroundTintMode = ids[3];
        int material_animateColorChanges = ids[4];

        if (a.hasValue(material_tint)) {
            ColorStateList color = getDefaultColorStateList((View) view, a, material_tint);
            if (color == null)
                color = a.getColorStateList(material_tint);
            if (color != null)
                view.setTintList(AnimatedColorStateList.fromList(color, animation -> ((View) view).postInvalidate()));
        }
        view.setTintMode(TintedView.modes[a.getInt(material_tintMode, 1)]);

        if (a.hasValue(material_backgroundTint)) {
            ColorStateList color = getDefaultColorStateList((View) view, a, material_backgroundTint);
            if (color == null)
                color = a.getColorStateList(material_backgroundTint);
            if (color != null)
                view.setBackgroundTintList(AnimatedColorStateList.fromList(color, animation -> ((View) view).postInvalidate()));
        }
        view.setBackgroundTintMode(TintedView.modes[a.getInt(material_backgroundTintMode, 1)]);

        if (a.hasValue(material_animateColorChanges))
            view.setAnimateColorChangesEnabled(a.getBoolean(material_animateColorChanges, false));
    }

    public static void initAnimations(AnimatedView view, TypedArray a, int[] ids) {
        if (((View) view).isInEditMode())
            return;

        int material_inAnimation = ids[0];
        if (a.hasValue(material_inAnimation)) {
            TypedValue typedValue = new TypedValue();
            a.getValue(material_inAnimation, typedValue);
            if (typedValue.resourceId != 0) {
                view.setInAnimator(AnimatorInflater.loadAnimator(((View) view).getContext(), typedValue.resourceId));
            } else {
               // view.setInAnimator(AnimUtils.Style.values()[typedValue.data].getInAnimator());
                view.setInAnimator(AnimUtils.get(true,typedValue.data));
            }
        }

        int material_outAnimation = ids[1];
        if (a.hasValue(material_outAnimation)) {
            TypedValue typedValue = new TypedValue();
            a.getValue(material_outAnimation, typedValue);
            if (typedValue.resourceId != 0) {
                view.setOutAnimator(AnimatorInflater.loadAnimator(((View) view).getContext(), typedValue.resourceId));
            } else {
               // view.setOutAnimator(AnimUtils.Style.values()[typedValue.data].getOutAnimator());
                view.setOutAnimator(AnimUtils.get(false,typedValue.data));
            }
        }
    }

    public static void initElevation(ShadowView view, TypedArray a, int[] ids) {
        int material_elevation = ids[0];
        int material_shadowColor = ids[1];

        float elevation = a.getDimension(material_elevation, 0);
        view.setElevation(elevation);
        if (elevation > 0)
            AnimUtils.setupElevationAnimator(((StateAnimatorView) view).getStateAnimator(), view);
        view.setElevationShadowColor(a.getColorStateList(material_shadowColor));
    }

    public static void initHtmlText(android.widget.TextView textView, TypedArray a, int id) {
        String string = a.getString(id);
        if (string != null)
            textView.setText(Html.fromHtml(string));
    }

    /**
     * @param context context
     * @param attr    attribute to get from the current theme
     * @return color from the current theme
     */
    public static int getThemeColor(Context context, int attr) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValue = new TypedValue();
        theme.resolveAttribute(attr, typedValue, true);
        return typedValue.resourceId != 0 ? context.getResources().getColor(typedValue.resourceId) : typedValue.data;
    }

    public static Context getThemedContext(Context context, AttributeSet attributeSet, int[] attrs, int defStyleAttr, int attr) {
        TypedArray a = context.obtainStyledAttributes(attributeSet, attrs, defStyleAttr, 0);
        if (a.hasValue(attr)) {
            int themeId = a.getResourceId(attr, 0);
            a.recycle();
            return new MaterialContextThemeWrapper(context, themeId);
        }
        return context;
    }

    public static int getDrawableAlpha(Drawable background) {
        if (background == null)
            return 255;
        background = background.getCurrent();
        if (background instanceof ColorDrawable)
            return ((ColorDrawable) background).getAlpha();
        if (background instanceof AlphaDrawable)
            return ((AlphaDrawable) background).getAlpha();
        return 255;
    }

    public static float getBackgroundTintAlpha(View child) {
        if (!(child instanceof TintedView))
            return 255;
        ColorStateList tint = ((TintedView) child).getBackgroundTint();
        if (tint == null)
            return 255;
        int color = tint.getColorForState(child.getDrawableState(), tint.getDefaultColor());
        return (color >> 24) & 0xff;
    }

    public static long getDefaultRevealDuration() {
        return defaultRevealDuration;
    }

    public static void setDefaultRevealDuration(long defaultRevealDuration) {
        Material.defaultRevealDuration = defaultRevealDuration;
    }

    public static void initStroke(StrokeView strokeView, TypedArray a, int[] ids) {
        int material_stroke = ids[0];
        int material_strokeWidth = ids[1];

        View view = (View) strokeView;
        ColorStateList color = getDefaultColorStateList(view, a, material_stroke);
        if (color == null)
            color = a.getColorStateList(material_stroke);

        if (color != null)
            strokeView.setStroke(AnimatedColorStateList.fromList(color, animation -> view.postInvalidate()));
        strokeView.setStrokeWidth(a.getDimension(material_strokeWidth, 0));
    }

    public static void initAutoSizeText(AutoSizeTextView view, TypedArray a, int[] ids) {
        int material_autoSizeText = ids[0];
        int material_autoSizeMinTextSize = ids[1];
        int material_autoSizeMaxTextSize = ids[2];
        int material_autoSizeStepGranularity = ids[3];
        view.setAutoSizeText(AutoSizeTextMode.values()[a.getInt(material_autoSizeText, 0)]);
        view.setMinTextSize(a.getDimension(material_autoSizeMinTextSize, 0));
        view.setMaxTextSize(a.getDimension(material_autoSizeMaxTextSize, 0));
        view.setAutoSizeStepGranularity(a.getDimension(material_autoSizeStepGranularity, 1));
    }

    public static int getThemeResId(Context context, int attr) {
        Resources.Theme theme = context.getTheme();
        TypedValue typedValueAttr = new TypedValue();
        theme.resolveAttribute(attr, typedValueAttr, true);
        return typedValueAttr.resourceId;
    }

   /* public static Menu getMenu(Context context, int resId) {
        Context contextWrapper = MaterialContextWrapper.wrap(context);
        Menu menu = new Menu(contextWrapper);
        MenuInflater inflater = new MenuInflater(contextWrapper);
        inflater.inflate(resId, menu);
        return menu;
    }*/

   /* public static Menu getMenu(Context context, android.view.Menu baseMenu) {
        Context contextWrapper = MaterialContextWrapper.wrap(context);
        Menu menu = new Menu(contextWrapper);
        for (int i = 0; i < baseMenu.size(); i++) {
            android.view.MenuItem menuItem = baseMenu.getItem(i);
            menu.add(menuItem.getGroupId(), menuItem.getItemId(), menuItem.getOrder(), menuItem.getTitle()).setIcon(menuItem.getIcon()).setVisible(menuItem.isVisible()).setEnabled(menuItem.isEnabled());
        }
        return menu;
    }*/

    public static float getRevealRadius(View view, int x, int y, float radius) {
        if (radius >= 0)
            return radius;
        if (radius != MAX_RADIUS)
            throw new InvalidParameterException("radius should be RevealView.MAX_RADIUS, 0.0f or a positive float");
        int w = Math.max(view.getWidth() - x, x);
        int h = Math.max(view.getHeight() - y, y);
        return (float) Math.sqrt(w * w + h * h);
    }

    public static void setTint(Drawable drawable, int tint) {
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTint(tint);
        } else {
            drawable.setColorFilter(new PorterDuffColorFilter(tint, PorterDuff.Mode.MULTIPLY));
        }
    }

    public static void setTintList(Drawable drawable, ColorStateList tint) {
        if (drawable instanceof TintAwareDrawable) {
            ((TintAwareDrawable) drawable).setTintList(tint);
        } else {
            drawable.setColorFilter(tint == null ? null : new PorterDuffColorFilter(tint.getColorForState(drawable.getState(), tint.getDefaultColor()), PorterDuff.Mode.MULTIPLY));
        }
    }

    public static void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
        if (drawable instanceof TintAwareDrawable)
            ((TintAwareDrawable) drawable).setTintMode(mode);
    }

    public static void logReflectionError(Exception e) {
        StackTraceElement cause = e.getStackTrace()[0];
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        Log.e("material", "This feature is implemented using reflection. " +
                "If you see this exception, something in your setup is not standard. " +
                "Please create an issue on https://github.com/ZieIony/material/issues. " +
                "Please provide at least the following information: \n" +
                " - device: " + Build.MANUFACTURER + " " + Build.MODEL + ", API " + Build.VERSION.SDK_INT + "\n" +
                " - method: " + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName() + "(...)\n" +
                " - cause: " + e.getClass().getName() + ": " + e.getMessage() + " at " + cause.getMethodName() + "(" + cause.getFileName() + ":" + cause.getLineNumber() + ")\n", e);
    }
}
