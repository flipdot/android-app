package org.flipdot.flipdotapp.helpers;

import android.widget.TextView;

/**
 * Created by daniel on 30.11.14.
 */
public class FontAwesomeHelper {
    public static void setIcon(TextView textView, String icon) {
        FontHelper.setFont(textView, Font.FontAwesome);
        textView.setText(icon);
    }
}
