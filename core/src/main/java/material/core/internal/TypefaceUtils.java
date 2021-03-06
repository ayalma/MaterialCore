package material.core.internal;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

import material.core.internal.Roboto;

public class TypefaceUtils {
    private static HashMap<String, Typeface> pathCache = new HashMap<>();
    private static HashMap<String, Typeface>[] familyStyleCache = new HashMap[4];

    static {
        familyStyleCache[Typeface.NORMAL] = new HashMap<>();
        familyStyleCache[Typeface.ITALIC] = new HashMap<>();
        familyStyleCache[Typeface.BOLD] = new HashMap<>();
        familyStyleCache[Typeface.BOLD_ITALIC] = new HashMap<>();
    }

    public static Typeface getTypeface(Context context, String path) {
        // get from cache
        Typeface t = pathCache.get(path);
        if (t != null)
            return t;

        // Roboto?
        for (Roboto style : Roboto.values()) {
            if (style.getPath().equals(path)) {
                t = loadRoboto(context, style);
                if (t != null)
                    return t;
                break;
            }
        }

        // load from asset
        t = Typeface.createFromAsset(context.getAssets(), path);
        if (t != null) {
            pathCache.put(path, t);
            return t;
        }

        return Typeface.DEFAULT;
    }

    public static Typeface getTypeface(Context context, String fontFamily, int textStyle) {
        // get from cache
        Typeface t = familyStyleCache[textStyle].get(fontFamily);
        if (t != null)
            return t;

        // Roboto?
        for (Roboto style : Roboto.values()) {
            if (style.getFontFamily().equals(fontFamily) && style.getTextStyle() == textStyle) {
                t = loadRoboto(context, style);
                if (t != null)
                    return t;
            }
        }

        // load from system res
        t = Typeface.create(fontFamily, textStyle);
        if (t != null) {
            familyStyleCache[textStyle].put(fontFamily, t);
            return t;
        }

        return Typeface.DEFAULT;
    }

    @Deprecated
    public static Typeface getTypeface(Context context, Roboto roboto) {
        // get from cache
        Typeface t = pathCache.get(roboto.getPath());
        if (t != null)
            return t;

        t = loadRoboto(context, roboto);
        if (t != null)
            return t;

        return Typeface.DEFAULT;
    }

    private static Typeface loadRoboto(Context context, Roboto roboto) {
        // try to load asset
        try {
            Typeface t = Typeface.createFromAsset(context.getAssets(), roboto.getPath());
            pathCache.put(roboto.getPath(), t);
            familyStyleCache[roboto.getTextStyle()].put(roboto.getFontFamily(), t);
            return t;
        } catch (Exception e) {
        }

        // try system font
        Typeface t = Typeface.create(roboto.getFontFamily(), roboto.getTextStyle());
        if (t != null) {
            pathCache.put(roboto.getPath(), t);
            familyStyleCache[roboto.getTextStyle()].put(roboto.getFontFamily(), t);
            return t;
        }

        return null;
    }
}
