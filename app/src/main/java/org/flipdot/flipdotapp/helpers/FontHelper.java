package org.flipdot.flipdotapp.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * Created by daniel on 14.12.14.
 */
public class FontHelper {
    public static void setFont(TextView textView, Font font) {
        Context context = textView.getContext();
        Typeface typeface = getTypeface(context, font.getFontFile());
        textView.setTypeface(typeface);
    }

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

    private static Typeface getTypeface(Context c, String assetPath) {
        synchronized (cache) {
            if (!cache.containsKey(assetPath)) {
                try {
                    Typeface t = Typeface.createFromAsset(c.getAssets(),
                            assetPath);
                    cache.put(assetPath, t);
                } catch (Exception e) {
                    Log.e("Typefaces", "Could not get typeface '" + assetPath
                            + "' because " + e.getMessage());
                    return null;
                }
            }
            return cache.get(assetPath);
        }
    }
}