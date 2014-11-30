package org.flipdot.flipdotapp.helpers;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by daniel on 30.11.14.
 */
public class FontAwesomeHelper {
    private Typeface _fontAwesomeTypeface;

    public FontAwesomeHelper(Context context) {
        _fontAwesomeTypeface = Typeface.createFromAsset(
                context.getAssets(),
                "fonts/fontawesome-webfont.ttf");
    }

    public void setIcon(TextView textView, String icon) {
        textView.setTypeface(this._fontAwesomeTypeface);
        textView.setText(icon);
    }
}
